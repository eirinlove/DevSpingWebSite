package org.yoon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.yoon.domain.BoardVO;
import org.yoon.domain.Criteria;

public interface BoardMapper {
	
	//�ܼ� �� ���
	public List<BoardVO> getList();
	//�� ����¡ ���
	public List<BoardVO> getListPaging(Criteria cri);
	//�� ���
	public void insert(BoardVO board);
	//�� ���[��ϵ� �۹�ȣ Ȯ�ΰ���]
	public void insertSelectKey(BoardVO board);
	//�� ��ȸ
	public BoardVO read(Long bno);
	//�� ����
	public int delete(Long bno);
	//�� ����
	public int update(BoardVO board);
	//����¡�� ���� �� ��ü ����
	public int getTotal(Criteria cri);
	//��ȸ�� ����
	public int visit(Long bno);
	//��ۼ� ����
	public void updateReplyCnt(@Param("bno") Long bno, @Param("amount") int amount); 
	//���ƿ� ����
	public int like(Long bno);
}
