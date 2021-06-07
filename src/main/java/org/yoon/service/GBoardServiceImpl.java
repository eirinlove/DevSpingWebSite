package org.yoon.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yoon.domain.Criteria;
import org.yoon.domain.GBoardAttachVO;
import org.yoon.domain.GBoardVO;
import org.yoon.mapper.GBoardAttachMapper;
import org.yoon.mapper.GBoardMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class GBoardServiceImpl implements GBoardService{

	@Setter(onMethod_= @Autowired)
	private GBoardMapper mapper;
	
	@Setter(onMethod_= @Autowired)
	private GBoardAttachMapper gattachMapper;
	
	@Transactional
	@Override
	public void register(GBoardVO gvo) {
		log.info("=============게시글 등록==============");
		 mapper.insertSelectKey(gvo);
		 
		 if(gvo.getAttachList() == null || gvo.getAttachList().size() <= 0) {
			 return;
		 }
		 gvo.getAttachList().forEach(attach -> {
			 attach.setBno(gvo.getBno());
			 gattachMapper.insert(attach);
		 });
	}

	@Transactional
	@Override
	public GBoardVO get(long bno) {
		log.info("=============게시글 상세보기==============");
		
		//조회수 올리기
		mapper.getMoreVisit(bno);
		
		return mapper.read(bno);
	}

	@Transactional
	@Override
	public boolean modify(GBoardVO gvo) {
		log.info("=============게시글 수정==============");
		
		gattachMapper.deleteAll(gvo.getBno());
		boolean modifyResult = mapper.update(gvo) ==1;
		
		if(modifyResult && gvo.getAttachList().size()>0) {
			gvo.getAttachList().forEach(attach -> {
				attach.setBno(gvo.getBno());
				gattachMapper.insert(attach);
			});
		}
		return modifyResult;
	}
	
	@Transactional
	@Override
	public boolean delete(long bno) {
		log.info("=============게시글 삭제==============");
		gattachMapper.deleteAll(bno);
		return mapper.delete(bno)==1;
	}

	@Override
	public List<GBoardVO> getList(Criteria cri) {
		log.info("=============게시글 목록 조회==============");
		return mapper.getListPaging(cri);
	}

	@Override
	public int getTotal(Criteria cri) {
		log.info("=============게시글 총 갯수==============");
		return mapper.getTotal(cri);
	}
	
	@Override
	public List<GBoardAttachVO> getAttachList(Long bno) {
		log.info("======글번호로 첨부파일 리스트 조회=======:"+bno);
		return gattachMapper.findByBno(bno);
	}

	@Transactional
	@Override
	public void recommend(HashMap map) {
		log.info("=============게시글 추천하기==============");
		//게시글 추천 수 증가
		mapper.getMoreRecommend(map.get("bno"));
		
		//추천아이디 및 글번호 저장
		mapper.recommend(map);
	}

	@Override
	public int checkRecommend(HashMap map) {
		log.info("=============게시글 추천여부 조회==============");
		return mapper.checkRecommend(map);
	}

	@Transactional
	@Override
	public void cancelRecommend(HashMap map) {
		log.info("=============게시글 추천취소==============");
		//게시글 추천 수 감소
		mapper.reduceRecommend(map.get("bno"));
		
		//추천아이디 및 글번호 삭제
		mapper.cancelRecommend(map);
	}

	@Override
	public List<GBoardVO> getNewList() {
		log.info("============갤러리게시판 최신글 조회==============");
		return mapper.getNewList();
	}

	@Override
	public List<GBoardVO> getBestList() {
		log.info("============갤러리게시판 베스트글 조회==================");
		return mapper.getBestList();
	}
	
	
	
	

}
