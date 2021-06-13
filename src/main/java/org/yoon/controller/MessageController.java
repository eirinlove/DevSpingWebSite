package org.yoon.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yoon.domain.MessageVO;
import org.yoon.service.GBoardService;
import org.yoon.service.MessageService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/message/*")
@Log4j
@AllArgsConstructor
public class MessageController {

	private MessageService service;
	
	@PostMapping("/recent")
	@ResponseBody
	public Map<String, Object> getRecentMessage(String userId){
		List<MessageVO> recentList = service.readRecentMessages(userId);
		Map<String, Object> result = new HashMap<>();
		result.put("result", recentList);
		return result;
	}
	
	@PostMapping("/insert")
	@ResponseBody
	public String insertMessage(MessageVO vo) {
		if(service.insert(vo)==1) {
			return "success";
		}
		return "Failed";
	}
	
	@GetMapping("/list")
	public String list(Model model, @RequestParam(value="type", defaultValue="R") String type) {
		
		String userId = "";
		userId = SecurityContextHolder.getContext().getAuthentication().getName();
		log.info(type);
		//로그인한 경우
		if(!userId.equals("anonymousUser")) {
			if(type.equals("R")) {
				log.info("======================받은 쪽지 목록====================");
				model.addAttribute("receptionList",service.receptionList(userId));
				log.info(service.receptionList(userId));
			}else {
				log.info("======================보낸 쪽지 목록====================");
				model.addAttribute("sentList",service.sentList(userId));
				log.info(service.sentList(userId));
			}
				model.addAttribute("type",type);
		}
		return "mypage/message";
	}
	
	@PostMapping("/read")
	@ResponseBody
	public String readMessage(long mno) {
		if(service.readMessage(mno)==1) {
			return "success";
		}
		return "failed";
	}
	
	@PostMapping("/cancel")
	@ResponseBody
	public String cancelMessage(long mno) {
			if(service.delete(mno)==1) {
				return "success";
			}else {
				return "Failed";
			}
	}
	@PostMapping("/delete")
	@ResponseBody
	public String deleteMessage(HttpServletRequest request) {

		String[] mnos = request.getParameterValues("mnos");
        for(String mno:mnos) {
        	service.delete(Long.parseLong(mno));
        }
		return "success";
	}
	
}
