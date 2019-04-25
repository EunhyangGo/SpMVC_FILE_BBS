package com.biz.fileup.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.biz.fileup.model.MemberVO;
import com.biz.fileup.service.LoginService;

import lombok.extern.slf4j.Slf4j;

/**
 * JAVA DOCS : 개발이 완료되고 문서화를 시킬때
 * 문서화 Tool을 활용할 수 있도록 작성하는 주석
 * 
 * @author hyang2lol@naver.com
 * @since : 2019-03-01
 * @see : 이 컨트롤러는 사용자 로그인, 로그아웃을 처리하기 위한 파일
 */

/*
 * sessionAttributes를 사용한 login을 HttpSession으로 변경
 */
@Slf4j
@Controller
@SessionAttributes({"memberVO"})
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	LoginService lService;
/*
 * 만약에 memberVO에 값이 없으면 memberVO에 값을 담아서 보여주고
 * 있으면 그 값을 유지시켜라
 * 이 메서드를 하면 모델을 굳이 하지 않아도 화면에 전송이 됨.
 * 
 * @ModelAttribute로 login_info를 선언하고 
 * login_info 초기화 method
 */

	@ModelAttribute("memberVO")
	public MemberVO login_info() {
		log.debug("NEW memberVO");
		return new MemberVO();
	}

	/**
	 * 
	 * @param memberVO
	 * @param model
	 * @param session
	 * @param s
	 * @return
	 * @see 로그인 폼을 실행하는 method
	 * 
	 */
	@RequestMapping(value="/login", method=RequestMethod.GET)
	public String login(@RequestParam(required=false) String LOGIN_MSG,
						@ModelAttribute MemberVO memberVO,
						Model model) {
		
		log.debug("LOGIN" + memberVO.toString());
		log.debug("LOGIN_MSG" + LOGIN_MSG);

		model.addAttribute("LOGIN_MSG" + LOGIN_MSG);
		model.addAttribute("BODY","LOGIN_FORM");
		return "home";
	}
	
	/*
	 * @ModleAttribute만 사용하면 지정된 객체가 실제 이름이 된다.
	 * 컨트롤러에서 form으로 데이터를 받는 용도로만 사용할때는
	 * 아무런 문제가 없다.
	 * 
	 * 하지만, 지금 프로젝트에서는 memberVO객체를
	 * 컨트롤러와 view(jsp)에서 공유해서 사용하고 있다.
	 * 
	 * 이럴때는 반드시 이름으로 강제로 지정해주는 것이 좋다
	 * @ModlAttribute("login_info")식으로 작성
	 * 
	 * 이제 login() method에서 제어권이 다른곳으로 이동되는 순간
	 * memeberVO는 마치 이름이 login_info로 바뀌는것과 같다.
	 * 
	 */
	
	@RequestMapping(value="login",method=RequestMethod.POST)
	public String login(@ModelAttribute MemberVO memberVO, Model model,
						HttpSession session) {
	
		// 만약 정상적인 로그인이면 vo에는 member정보가 있을것이고
		// 그렇지 않으면 null이 있다.
		MemberVO vo = lService.getMemberInfo(memberVO);
		if(vo != null) {
			/*
			 * 기존에 sessionAttribute에 담았던 login_info를
			 * HttpSession으로 분리
			 */
			session.setAttribute("login_info", vo);
			log.debug(memberVO.toString());
		}else {
			
			//로그인 method가 호출되면 
			//member의 로그인 관련 정보가 정상이 아니라도
			//session이 이미 시작이 되어버린다.
			
			//그래서 로그인에 실패했으면 세션정보를 반드시 지워줘야 안전하다.
			session.removeAttribute("login_info");
		}
		return "redirect:/";
	}
	
	@RequestMapping(value="logout", method=RequestMethod.GET)
	public String logout(@ModelAttribute MemberVO memberVO,
			HttpSession session, SessionStatus status) {
		
		log.debug("LOGOUT");
		//session을 제거
		//session완료, 만료되었다라고 표현
		session.removeAttribute("login_info");
		status.setComplete();
		return "redirect:/";
	}
}
