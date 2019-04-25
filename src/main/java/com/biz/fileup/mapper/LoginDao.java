package com.biz.fileup.mapper;

import org.apache.ibatis.annotations.Select;

import com.biz.fileup.model.MemberVO;

public interface LoginDao {

	
	@Select("SELECT * FROM tbl_member WHERE m_userid = #{m_userid}")
	public MemberVO getMemberInfo(String m_userid);
}
