package org.yoon.service;

import java.util.List;

import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;
import org.yoon.domain.ReplyPageDTO;
import org.yoon.domain.ReplyVO;

public interface ReplyService {
	
	//��� ���
	public int register(ReplyVO reply);
	//��� ��ȸ
	public ReplyVO get(Long bno);
	//��� ����
	public int remove(Long bno);
	//��� ����
	public int modify(ReplyVO reply);
	//��� ����Ʈ ���+����¡ ����
	public ReplyPageDTO getList(Criteria cri,Long bno);

}
