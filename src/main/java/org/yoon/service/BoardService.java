package org.yoon.service;

import java.util.List;

import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;

public interface BoardService {
	
	//�� ���
	public void register(BoardVO board);
	//�� ��ȸ
	public BoardVO get(Long bno);
	//�� ����
	public int remove(Long bno);
	//�� ����
	public int modify(BoardVO board);
	//�� ����Ʈ ���+����¡ ����
	public List<BoardVO> getList(Criteria cri);
//	//����¡�� ���� �� ���
//	public List<BoardVO> getListPaging(Criteria cri);
	//����¡�� ���� �� ��ü ����
	public int getTotal(Criteria cri);
	//��ȸ��
	public int visit(Long bno);
	//�ش� ���� ÷������ ��������
	public List<BoardAttachVO>getAttachList(Long bno);
	//���ƿ�
	int Like(Long bno);
}
	
