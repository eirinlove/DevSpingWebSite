package org.yoon.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.yoon.domain.Criteria;
import org.yoon.domain.GBoardVO;


public interface GBoardMapper {

	//게시글 목록 조회
	public List<GBoardVO> getListPaging(Criteria cri);
	//게시글 등록
	public void insert(GBoardVO gBoard);
	//글번호 확인 및 게시글 등록
	public void insertSelectKey(GBoardVO gBoard);
	//게시글 보기
	public GBoardVO read(long bno);
	//게시글 삭제
	public int delete(long bno);
	//게시글 수정
	public int update(GBoardVO vo);
	//게시글 총 개수
	public int getTotal(Criteria cri);
	//조회수 증가
	public int getMoreVisit(long bno);
	//게시글 추천
	public void recommend(HashMap map);
	//게시글 추천수 증가
	public int getMoreRecommend(Object bno);
	//게시글 추천수 감소
	public int reduceRecommend(Object bno);
	//추천여부 조회
	public int checkRecommend(HashMap map);
	//추천취소하기
	public void cancelRecommend(HashMap map);
	//최신글 가져오기
	public List<GBoardVO> getNewList();
	//베스트글 가져오기
	public List<GBoardVO> getBestList();
	
}
