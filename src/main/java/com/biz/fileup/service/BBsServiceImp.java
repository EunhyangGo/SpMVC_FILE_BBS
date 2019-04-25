package com.biz.fileup.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.biz.fileup.mapper.BoardDao;
import com.biz.fileup.model.BoardVO;
@Service
public class BBsServiceImp implements BBsService {

	@Autowired
	BoardDao bDao;
	
	@Override
	public List<BoardVO> selectAll() {

		List<BoardVO> bbsList =bDao.selectAll();
		
		return bbsList;
	}

	//@Transactional
	@Override
	public BoardVO findById(long id) {
		// TODO Auto-generated method stub
	
		BoardVO vo = bDao.findById(id);
		return vo;
	}

	@Override
	public List<BoardVO> findByUserId(String b_userid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int insert(BoardVO boardVO) {
		
		int ret =bDao.insert(boardVO);
		// TODO Auto-generated method stub
		return ret;
	}

	@Override
	public int update(BoardVO boardVO) {
		// TODO Auto-generated method stub
		return bDao.update(boardVO);
	}

	@Override
	public int delete(long id) {
		// TODO Auto-generated method stub
		return bDao.delete(id);
	}


	/*
	 * (non-Javadoc)
	 * 
	 * @see com.biz.fileup.service.BBsService#UpdateHit(long, java.lang.String)
	   2번 이상의 SQL문이 실행되는 경우
	   SQL 실행이 모두 완료되기전에 
	   다른 사용자(또는 접속)에 의해서 메서드가 실행되면
	   데이터 신뢰도에 문제가 발생할 수 있다
	   
	   그러한 문제를 막기 위해서 RDBMS에서는 
	   2번 이상의 SQL문을 Transaction이라는 작업 그룹으로 묶어 주어야 한다.
	   
	   스프링과 mytis 프로젝트에서는
	   mybatis-context.xml 또는 MyBatisConfig에서
	   DataTransactionManager를 bean으로 설정해 두고
	   
	   transaction이 필요한 mehtod에서
	   @Transactional을 설정해 두면
	   나머지는 Spring, myBatis, DataSource들끼리 서로 협력하여
	   모든 처리를 대신 해준다.
	   
	   주의할 점
	   Config에서 transacntionManager라는 이름으로 method를 설정
	   해야하고, 
	   Config.java로 설정할때는 @EnableTransactionmanager()로 설정한다.
	   
	   MySQL은 기본 DB엔진에서는 자체적으로 Transaction을 지원하지 않는다.
	   그래서 MySQL을 처음 설치할때 DB엔진은 innoDB로 설정을 해야한다.
	   
	   DataSource를 설정할때 commons-dbcp2를 사용하면 가장 매칭이 잘된다.
	   
	  
	 
	 */
	@Transactional
	@Override
	public BoardVO UpdateHit(long id, String b_userid) {
		
		BoardVO vo = bDao.findById(id);
		if(!vo.getB_userid().equalsIgnoreCase(b_userid)) {
			bDao.boardHit(id);
		}
		return vo;
	}

}
