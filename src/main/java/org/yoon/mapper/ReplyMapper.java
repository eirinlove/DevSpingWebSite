package org.yoon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.yoon.domain.Criteria;
import org.yoon.domain.ReplyVO;

public interface ReplyMapper {

	//댓글 등록	
	public int insert(ReplyVO vo);
	//댓글 조회
	public ReplyVO read(Long rno);
	//댓글 삭제
	public int delete(Long rno);
	//댓글 수정
	public int update(ReplyVO reply);
	//두개이상 데이터 파라미터로 전 1.map 2.별도객체 3.param
	public List<ReplyVO> getListPaging(@Param("cri") Criteria cri,@Param("bno") Long bno);
	//댓글 숫자 파악
	public int getCountByBno(Long bno);
	
	public int hidecheck(ReplyVO hide); 
	// 비밀글 체크 
	
}
