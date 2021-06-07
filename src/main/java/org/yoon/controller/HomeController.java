package org.yoon.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.yoon.service.GBoardService;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
public class HomeController {
	
	@Autowired
	private GBoardService gservice;
	
	//최신글 및 인기글 가져와서 메인 보내기
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String main(Model model) {
		log.info("================메인 페이지===================");
		model.addAttribute("gNewList",gservice.getNewList());
		model.addAttribute("gBestList",gservice.getBestList());
		
		return "main";
	}
	
}
