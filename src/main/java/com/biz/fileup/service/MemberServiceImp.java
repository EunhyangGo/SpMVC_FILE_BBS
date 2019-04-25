package com.biz.fileup.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.biz.fileup.mapper.MemberDao;
import com.biz.fileup.model.MemberVO;

import lombok.extern.slf4j.Slf4j;
/*
 * memberSevice를 구현하기위한 명세서(구현체)
 * 인터페이스의 장점:
 * Controller->Service의 메서드를 호출하도록 만들어져있음
 * service에 있는 메서드를 호출해서 어떤일을 호출해야하는데 서비스가 안만들어져있음
 * 그럼 마우스 올려서 새로 만듦.
 * 
 * 이렇게 되면 메서드이름의 통일성이 사라짐.
 * 그러면 나중에 서비스 통합을할때 많은 어려움이 발생함.
 * 그래서 컨트롤러에서 인터페이스를 하나 만들어두면 어떤 사람이 컨트롤러에서 인서트를 하겠다라하면
 * 최소한 이름은 다 같게 됨.
 * 
 * 즉 insert라는 메서드안에 안에 들어가는 매개변수 이름도 같게됨. return값도 다 같게됨
 * 
 * 여러개의 클래스가 한 클래스에 규칙->명세화 시켜놓은게 인터페이스
 */

/*
 * 구현체
 * 	Service interface를 implement하여
 * 	구현한 실체(메서드의 구체적인 기능들을 코딩한 클래스)
 * 
 * 인터페이스와 구현체의 사용
 * 일반적으로 클래스를 사용하기 위해서는 
 * 클래스 객체 = new 클래스()
 * (↑클래스의이름) (↑생성자)
 * 
 * 인터페이스와 구현체일 경우는
 * 	인터페이스 객체 = new 구현체()의 관계로 형성한다.
 * 	List<MemberVO> memberList = new ArrayList<MemberVO>();
 * 
 * 	↑리스트형 인터페이스 				↑구현체생성자
 * 	※ List memberList = new List 형식은 사용 불가
 * 
 * 자바에서는 어떤 클래스의 인터페이스가 있는 경우는 
 * 가능하면 인터페이스로 선언을 하고 클래스로 생성을 하라고 한다.
 * 일반적으로 아래와 같으나
 * 		ArrayList<MemberVO> memberList = new ArrayList<MemberVO>();
 * 권장하는 방식은 다음과 같다.
 * 		List<MemberVO> memberList = new ArrayList<MemberVO>();	
 */
@Slf4j
@Service
public class MemberServiceImp implements MemberService {

	@Autowired
	MemberDao mDao;
	
	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Override
	public List<MemberVO> selectAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MemberVO findByUserID(String m_userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(MemberVO memberVO) {
		// TODO Auto-generated method stub
		
		// 비밀번호를 암호화 시키자
		String plainPass = memberVO.getM_password();
		
		// SHA 256 방식 암호화
		String cryPass = encoder.encode(plainPass);
		
		log.debug("비밀번호: " + plainPass);
		log.debug("암호 비밀번호:" + cryPass);
		
		memberVO.setM_password(cryPass);
		int ret = mDao.insert(memberVO);
		return ret;
	}

	@Override
	public int update(MemberVO memberVO) {

		String plainPass = memberVO.getM_password();
		String cyptPass = encoder.encode(plainPass);
		
		memberVO.setM_password(cyptPass);
		int ret = mDao.update(memberVO);
		return ret;
	}

	@Override
	public int delete(String m_userid) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int save(@Valid MemberVO memberVO) {
		
		int ret = 0;
		// insert를 수행할 것인가
		// update를 수행할 것인가
		
		MemberVO vo = mDao.findByUserId(memberVO.getM_userid()); //userid를 받아서
		if(vo ==null) {
			//mDao.insert(memberVO);
			this.insert(memberVO);//insert메소드 호출
			ret=1;
		}else {
			String rawString = memberVO.getM_password();
			String encodeString = vo.getM_password();
			
			// 비밀번호가 일치하지 않으면 update가 되지 않도록 한다
			if(encoder.matches(rawString, encodeString)) {
				this.update(memberVO);
				ret=2;
			}else {
				// update가 안되면 안되는 이유를 알려주는 코드 필요
				ret =-1;
			}
			
		}
		return ret;
	}

}
