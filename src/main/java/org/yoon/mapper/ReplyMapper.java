package org.yoon.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.yoon.domain.Criteria;
import org.yoon.domain.ReplyVO;

public interface ReplyMapper {

	//��� ���	
	public int insert(ReplyVO vo);
	//��� ��ȸ
	public ReplyVO read(Long rno);
	//��� ����
	public int delete(Long rno);
	//��� ����
	public int update(ReplyVO reply);
	//�ΰ��̻� ������ �Ķ���ͷ� �� 1.map 2.������ü 3.param
	public List<ReplyVO> getListPaging(@Param("cri") Criteria cri,@Param("bno") Long bno);
	//��� ���� �ľ�
	public int getCountByBno(Long bno);
	
}
