package com.biz.fileup.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.biz.fileup.mapper.LoginDao;
import com.biz.fileup.model.MemberVO;

@Service
public class LoginServiceImp implements LoginService {

	@Autowired
	LoginDao lDao;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Override
	public MemberVO getMemberInfo(MemberVO loginVO) {
		// TODO Auto-generated method stub
	
		/*
		 * 정상적인 login인지 확인하기 위해 
		 * userId와 password를 조회해서 일치하는지 알아보아야하지만
		 * Password를 암호화하였고,
		 * 지금 암호화 한 password는 단방향 암호화 방식이다.
		 * 
		 * 단방향 암호화된 비밀번호가 일치하는지 알아보는 방법
		 * 1. 로그인 할 때 입력한 비밀번호를 다시 암호화를 한다.
		 * 2. 생성된 암호 문자열을 
		 * 3. DB에서 가져온 문자열과 비교해서 일치하는지 알아 본다.
		 * 
		 * 그런데
		 * BCryp...암호화 방식에서는 모든것을 내장된(기본제공되는)
		 * method를 통해서만 가능하다.
		 * 
		 */
		
		String m_userid = loginVO.getM_userid();
		MemberVO vo = lDao.getMemberInfo(m_userid);
		
		String crypPass = vo.getM_password();
		String encodedPassword = vo.getM_password();
		
		String plainPass = loginVO.getM_password();
		String rawPassword = loginVO.getM_password();
		
		boolean match = encoder.matches(rawPassword, encodedPassword);
		
		/*
		 * match가 true이면 vo를 리턴
		 * true인것은 비밀번호가 일치했다는 뜻.
		 * 
		 */
		if(match) {
			// 비밀번호가 일치했다면 db에서 읽어온 member의 정보를
			// controller에게 리턴하고
			return vo;
		}else {
			// 그렇지 않으면 null을 리턴한다.
			return null;
		}
		
		
	}

}
