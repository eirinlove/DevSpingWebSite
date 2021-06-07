package org.yoon.service;

import java.util.HashMap;
import java.util.List;

import org.yoon.domain.Criteria;
import org.yoon.domain.GBoardAttachVO;
import org.yoon.domain.GBoardVO;


public interface GBoardService {

	//게시글 등록
	public void register(GBoardVO gvo);
	//게시글 상세보기
	public GBoardVO get(long bno);
	//게시글 수정
	public boolean modify(GBoardVO gvo);
	//게시글 삭제
	public boolean delete(long bno);
	//게시글 목록 조회
	public List<GBoardVO> getList(Criteria cri);
	//페이징에 쓰일 글 전체 개수
	public int getTotal(Criteria cri);
	//글번호로 첨부파일 리스트 조회
	public List<GBoardAttachVO> getAttachList(Long bno);
	//추천하기
	public void recommend(HashMap map);
	//추천여부 조회
	public int checkRecommend(HashMap map);
	//추천 취소
	public void cancelRecommend(HashMap map);
	//최신글 조회
	public List<GBoardVO> getNewList();
	//베스트글 조회
	public List<GBoardVO> getBestList();
}
