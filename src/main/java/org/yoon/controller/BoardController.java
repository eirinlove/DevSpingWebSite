package org.yoon.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.PageDTO;
import org.yoon.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/board/*")
@AllArgsConstructor // ��� �ʵ尪 ������ ����
public class BoardController {

	private BoardService service;
	
	//�ۻ����� �Բ� ����Ǵ� ÷������ ����
	private void deleteFiles(List<BoardAttachVO> attachList) {
		if(attachList == null || attachList.size() == 0) {
			return;
		}
		log.info("�ۿ� ���Ե��ִ� ÷������ ����");
		log.info(attachList);
		
		attachList.forEach(attach ->{
			try {
				//java.nio.file�� Path�̿�->������ ���� ��� ��������
				Path file = Paths.get("c:\\upload1\\"+attach.getUploadPath()+"\\"+attach.getUuid()+"_"+attach.getFileName());
				//������� ������ �����ϴ��� Ȯ��
				Files.deleteIfExists(file);
				//������ Ÿ���� �̹����� �����ϸ�?
				if(Files.probeContentType(file).startsWith("image")) {
					//����� ��� �����ͼ�
					Path thumNail = Paths.get("c:\\upload1\\"+attach.getUploadPath()+"\\s_"+attach.getUuid()+"_"+attach.getFileName());
					//����
					Files.delete(thumNail);		
				}
			} catch (Exception e) {
				log.error("÷������ ���� ����: "+e.getMessage());
			}//End catch
		}); //End forEach
	}//End deleteFiles
	
	
	//�� ����Ʈ ���
	@GetMapping("/list")
	public void list(Criteria cri, Model model) {
		log.info("�� ��� ����(list)");
		log.info("======[list]: ");
		//�� ���� �ѱ��
		model.addAttribute("list", service.getList(cri));
		
		int total = service.getTotal(cri);
		//����¡ ����
		model.addAttribute("pageMaker", new PageDTO(cri,total));
	}
	
	//�� ��� �������� �̵�
	@GetMapping("/register")
	public void register() {
	
		}

	//�� ���
	@PostMapping("/register")
	//� ����ڵ� �α��� �����ѻ����
	@PreAuthorize("isAuthenticated()")
	public String register(BoardVO board, RedirectAttributes rttr) {
		log.info("�� ���: " + board);
		if(board.getAttachList()!=null) {
			board.getAttachList().forEach(attach -> log.info(attach));
		}
		
		service.register(board);
		rttr.addFlashAttribute("result", board.getBno()); // ȭ��ܿ��� oo��° ���� ��ω���� ���� ����
		
		log.info(board.getBno());
		return "redirect:/board/list";
	}
	
	//�� ��ȸ �������� �̵�
	@GetMapping({"/get", "/modify"})
	public void get(@RequestParam("bno") Long bno, Model model) {
		log.info("/get");
		//��ȸ�� ����
		service.visit(bno);
		model.addAttribute("board",service.get(bno));
		
	}
	
	@PreAuthorize("principal.username == #writer")
	//�� ����
	@PostMapping("/modify")
	public String modify(BoardVO board, @ModelAttribute("cri") Criteria cri,RedirectAttributes rttr) {
		log.info("/modify");
		
		if(service.modify(board)==1) {
			rttr.addFlashAttribute("result","success"); // ȭ������� ���� �޽��� ����
		}
		
		return "redirect:/board/list";
	}
	
	@PreAuthorize("principal.username == #writer")
	//�� ����
	@PostMapping("/remove")
	public String remove(@RequestParam("bno") Long bno, @ModelAttribute("cri") Criteria cri, RedirectAttributes rttr) {
		log.info("����: "+bno);
		//���� ��� �������
		List<BoardAttachVO>list=service.getAttachList(bno);
		
		//���� �����Ǹ�?
		if(service.remove(bno)==1) {
			//������ ���ϸ�� ���� ���� �����ϱ�
			deleteFiles(list);
			rttr.addFlashAttribute("result","success"); // ȭ������� ���� �޽��� ����
		}
		
		return "redirect:/board/list"+cri.getListLink();
	}

	//���� ��������
	@GetMapping(value="/getAttachList", produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>>getAttachList(Long bno){
		log.info("getAttachList"+bno);
		return new ResponseEntity<>(service.getAttachList(bno),HttpStatus.OK);
	}
	
	//�ش�� ���ƿ� 
	@PostMapping("/like")
	@ResponseBody
	public int like(Long bno){
		log.info(bno+"���ƿ� ��ư Ŭ��");
		return service.Like(bno);
	}
	
	
}