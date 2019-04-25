package com.biz.fileup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.biz.fileup.model.PageVO;
import com.biz.fileup.service.PageService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class PageController {

	@Autowired
	PageService ps;

	@RequestMapping(value="get_page",method=RequestMethod.GET)
	public String getPage(@RequestParam(name="page_no",required=false,defaultValue="0") long page_no, Model model) {
		
		log.debug("PageNO" + page_no);
		
		PageVO pageVO = ps.page_select(page_no);
		model.addAttribute("BBS_LIST", ps.pageList(pageVO));
		model.addAttribute("BODY","BBS_LIST");
		model.addAttribute("PAGE", pageVO);
		return "home";
	}
}
