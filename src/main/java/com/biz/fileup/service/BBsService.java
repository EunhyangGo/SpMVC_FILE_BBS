package com.biz.fileup.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.biz.fileup.model.BoardVO;


public interface BBsService {

	
	public List<BoardVO> selectAll();
	public BoardVO findById(long id);
	public List<BoardVO> findByUserId(String b_userid);
	
	public BoardVO UpdateHit(long id, String b_userid);
	
	public int insert(BoardVO boardVO);
	public int update(BoardVO boardVO);
	public int delete(long id);
}
