package org.yoon.controller;

import java.security.Principal;
import java.util.List;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus; 
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.MemberAttachVO;
import org.yoon.domain.MemberVO;
import org.yoon.domain.ReplyVO;
import org.yoon.mapper.MemberMapper;
import org.yoon.security.domain.CustomUser;
import org.yoon.service.BoardService;
import org.yoon.service.MemberService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Log4j
@Controller
@AllArgsConstructor
@RequestMapping("/member/*")
public class CommonController {
	
	private MemberService service;
	
	//�̸��� ����
	private JavaMailSender mailSender;
	
	
	//ajax�� �޾ƿ� �̸��� 
	@GetMapping("/mailCheck")
	@ResponseBody
	public String mailCheck(String email) {
		log.info("�̸��� ������ ���� Ȯ��");
		log.info("������ȣ: "+email);
		
		//������ȣ ��������
		Random random = new Random();
		int CheckNum = random.nextInt(888888)+111111;
		
		//���� ��û�� �̸��Ͽ��� ������ȣ ������
		String setFrom = "pulpul8282@naver.com";
		String toMail = email;
		String title = "ȸ������ ���� �̸��� �Դϴ�.";
		String content = "Ȩ�������� �湮���ּż� �����մϴ�."+"<br><br>"+"���� ��ȣ��"+CheckNum+"�Դϴ�."+"<br>"+"�ش� ������ȣ�� ������ȣ Ȯ�ζ��� �����Ͽ� �ּ���.";
		
		//�̸��� ���� �ڵ�
		try {
			MimeMessage message = mailSender.createMimeMessage();
			MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content,true);
			mailSender.send(message);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		String num = Integer.toString(CheckNum);
		log.info("������ȣ: "+num);
		
		return num;
	}
	
	//������ ���� ��������
	@GetMapping(value="/getAttachList", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>>getAttachList(String userid){
		log.info("getAttachList: "+userid);
		log.info(service.getAttachList(userid));
		
		return new ResponseEntity<>(service.getAttachList(userid),HttpStatus.OK);
	}
	
	
	//���������� ����
	@PreAuthorize("isAuthenticated()")
	@PostMapping("modify")
	public String modify(MemberVO vo,RedirectAttributes rttr) {
		log.info("/modify");
		int result = service.update(vo);
		if(result>0) {
			rttr.addFlashAttribute("result","success");
		}
		return "redirect:/member/myProfile";
	}
	
	@PreAuthorize("isAuthenticated()")
	//���������� â���� �̵�
	@GetMapping("myProfile")
	public void profile(Principal principal,Model model) {
		
		log.info("���������� â���� �̵�");
		log.info("�������̵�: "+principal.getName());
		String userid=principal.getName();
		MemberVO vo=service.read(userid);
		model.addAttribute("user", vo);
	}
	
	@PreAuthorize("isAuthenticated()")
	//���������� â���� �̵�
	@GetMapping("modify")
	public void profile() {
		log.info("���������� ������������ �̵�");
	
	}
	
	
	//ȸ������ â���� �̵�
	@GetMapping("customRegister")
	public void Register() {
		log.info("ȸ������ â���� �̵�");
	}
	
	//ȸ�� ���
	@PostMapping("/customRegister")
	public String Register(MemberVO vo, RedirectAttributes rttr) {
		log.info("ȸ����� post");
				
		service.insert(vo);	
	
		return "redirect:/member/customLogin";
	} 

	//�α��� â���� �̵�
	@GetMapping("/customLogin")
	public void login(String error, String logout, Model model) {
		log.info("error: "+error);
		log.info("logout: "+logout);
		
		if(error != null) {
			model.addAttribute("error", "�α��� ���� �߻�!! ������ Ȯ�����ּ���");
		}
		if(logout != null) {
			model.addAttribute("logout", "�α׾ƿ�");
		}
	}
	
	//�α׾ƿ� â���� �̵�
	@GetMapping("/customLogout")
	public void logoutGet() {
		log.info("�α׾ƿ�");
	}
	
	@PostMapping("/customLogout")
	public void logoutPost() {
		log.info("post �α׾ƿ�");	
	}
	
	//id�ߺ�üũ
	@GetMapping("/idCheckService")
	@ResponseBody
	public int idCheck(@RequestParam("userid") String id) {
		int result=service.idCheck(id);
		if(result == 1) {
			return 1;
		}else {
			return 0;
		}
	}
	
	
}
