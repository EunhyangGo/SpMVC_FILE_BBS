package com.biz.fileup.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;

import com.biz.fileup.model.BoardVO;

public interface BoardDao {

	// 게시판은 나중에 작성된 글(최신을)이 제일 먼저 출력된다.
	// 그래서 날짜 시간을 역순(내림찯순)으로 정렬한다
	// ORder BY는 기본값이 오름차순 정렬 생략하면 ASC가 생략된것과 같다
	// 최신글을 제일 먼저 보일려면 ORDER BY DESC으로 설정해야한다.
	@Select(" SELECT * FROM tbl_board ORDER BY b_date,b_time DESC")
	public List<BoardVO> selectAll();
	
	@Select(" SELECT * FROM tbl_board WHERE id =#{id}")
	public BoardVO findById(long id);
	
	@Update("UPDATE tbl_board SET b_hit = b_hit+1 WHERE id = #{id}")
	public int boardHit(long id);
	
	@Select(" SELECT * FROM tbl_board WHERE b_userid =#{b_userid}"
			+ "ORDER BY b_date ")
	public List<BoardVO> findByUserId(String b_userid);
	
	/*
	 * @SelectKey는 @InsertProvider가 실행되기 전(before=true인경우)이나,
	 * 		실행된 후(before=false)에 statement에 지정된 SQL문을 실행하라.
	 * 
	 * 이경우는 before=true이므로 Insert이전에 실행을 해서
	 * kyeProperty로 지정된 변수에 저장
	 * 이때 id의 자료형은 Long형이다.
	 */
	@SelectKey(before=true, keyProperty="id",resultType=Long.class,
				statement=" SELECT ROUND(DBMS_RANDOM.VALUE(0,9999999999),0) FROM DUAL ")
	@InsertProvider(type=BoardSQL.class,method="board_insert_sql")
	public int insert(BoardVO boardVO);
	
	@UpdateProvider(type=BoardSQL.class,method="board_update_sql")
	public int update(BoardVO boardVO);
	
	@Delete("DELETE FROM tbl_board WHERE id=#{id}")
	public int delete(long id);
}
