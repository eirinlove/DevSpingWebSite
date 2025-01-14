package org.yoon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.GBoardVO;
import org.yoon.mapper.AttachMapper;
import org.yoon.mapper.BoardMapper;
import org.yoon.mapper.ReplyMapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j;

@Log4j
@Service
@AllArgsConstructor
public class BoardServiceImpl implements BoardService {

	private BoardMapper mapper;
	private AttachMapper AttachMapper;
	private ReplyMapper ReplyMapper;

	@Transactional
	@Override
	public void register(BoardVO board) {
		
		log.info("==========글 등록========:    "+ board);
		mapper.insertSelectKey(board);
		//글 등록시 파일 정보
		if (board.getAttachList() == null || board.getAttachList().size() <= 0) {
			return;
		}
		board.getAttachList().forEach(attach -> {
			attach.setBno(board.getBno());
			AttachMapper.insert(attach);
		});
	
	}

	@Override
	public BoardVO get(Long bno) {

		log.info("==========글 조회========:    "+ bno);
		return mapper.read(bno);
	}

	@Transactional
	@Override
	public int remove(Long bno) {
		//파일 같이 삭제
		AttachMapper.deleteAll(bno);
		
		//
		return mapper.delete(bno);
	}

	@Transactional
	@Override
	public int modify(BoardVO board) {
		log.info("==========글 수정========:    "+ board);
		
		//기존의 첨부파일 삭제한후 다시 첨부파일 추가하는 방식으로 동작
		AttachMapper.deleteAll(board.getBno());
		int cnt=mapper.update(board);
		
		//1.글 수정이 완료, 2.수정된 글의 파일이 존재시 3. 수정된 글의 파일 크기가 존재시  앞선 3개의 조건이 만족하면 
		if(cnt!=0 && board.getAttachList()!=null && board.getAttachList().size()>0) {
			//새로운 파일 등록(파일 수정 등록)
			board.getAttachList().forEach(attach ->{
				attach.setBno(board.getBno());
				AttachMapper.insert(attach);
			});
		}
		
		return cnt;
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("==========글 리스트 출력========");
		return mapper.getListPaging(cri);
	}

//	@Override
//	public List<BoardVO> getListPaging(Criteria cri) {
//		log.info("==========글 리스트 페이징 출력========");
//		return mapper.getListPaging(cri);
//	}

	@Override
	public int getTotal(Criteria cri) {
		// TODO Auto-generated method stub
		return mapper.getTotal(cri);
	}

	@Override
	public int visit(Long bno) {
		log.info("조회수 증가");
		return mapper.visit(bno);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		log.info(bno+"의 첨부파일 가져오기");
		return AttachMapper.findByBno(bno);
	}

	@Override
	public int Like(Long bno) {
		log.info(bno+"좋아요!");
		return mapper.like(bno);
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
