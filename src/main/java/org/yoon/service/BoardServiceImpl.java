package org.yoon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
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
		
		log.info("==========�� ���========:    "+ board);
		mapper.insertSelectKey(board);
		//�� ��Ͻ� ���� ����
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

		log.info("==========�� ��ȸ========:    "+ bno);
		return mapper.read(bno);
	}

	@Transactional
	@Override
	public int remove(Long bno) {
		//���� ���� ����
		AttachMapper.deleteAll(bno);
		
		//�ۻ���
		return mapper.delete(bno);
	}

	@Transactional
	@Override
	public int modify(BoardVO board) {
		log.info("==========�� ����========:    "+ board);
		
		//������ ÷������ �������� �ٽ� ÷������ �߰��ϴ� ������� ����
		AttachMapper.deleteAll(board.getBno());
		int cnt=mapper.update(board);
		
		//1.�� ������ �Ϸ�, 2.������ ���� ������ ����� 3. ������ ���� ���� ũ�Ⱑ �����  �ռ� 3���� ������ �����ϸ� 
		if(cnt!=0 && board.getAttachList()!=null && board.getAttachList().size()>0) {
			//���ο� ���� ���(���� ���� ���)
			board.getAttachList().forEach(attach ->{
				attach.setBno(board.getBno());
				AttachMapper.insert(attach);
			});
		}
		
		return cnt;
	}

	@Override
	public List<BoardVO> getList(Criteria cri) {
		log.info("==========�� ����Ʈ ���========");
		return mapper.getListPaging(cri);
	}

//	@Override
//	public List<BoardVO> getListPaging(Criteria cri) {
//		log.info("==========�� ����Ʈ ����¡ ���========");
//		return mapper.getListPaging(cri);
//	}

	@Override
	public int getTotal(Criteria cri) {
		// TODO Auto-generated method stub
		return mapper.getTotal(cri);
	}

	@Override
	public int visit(Long bno) {
		log.info("��ȸ�� ����");
		return mapper.visit(bno);
	}

	@Override
	public List<BoardAttachVO> getAttachList(Long bno) {
		log.info(bno+"�� ÷������ ��������");
		return AttachMapper.findByBno(bno);
	}

	@Override
	public int Like(Long bno) {
		log.info(bno+"���ƿ�!");
		return mapper.like(bno);
	}


	
}