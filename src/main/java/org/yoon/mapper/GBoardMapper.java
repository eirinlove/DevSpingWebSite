package org.yoon.mapper;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.yoon.domain.Criteria;
import org.yoon.domain.GBoardVO;


public interface GBoardMapper {

	//�Խñ� ��� ��ȸ
	public List<GBoardVO> getListPaging(Criteria cri);
	//�Խñ� ���
	public void insert(GBoardVO gBoard);
	//�۹�ȣ Ȯ�� �� �Խñ� ���
	public void insertSelectKey(GBoardVO gBoard);
	//�Խñ� ����
	public GBoardVO read(long bno);
	//�Խñ� ����
	public int delete(long bno);
	//�Խñ� ����
	public int update(GBoardVO vo);
	//�Խñ� �� ����
	public int getTotal(Criteria cri);
	//��ȸ�� ����
	public int getMoreVisit(long bno);
	//�Խñ� ��õ
	public void recommend(HashMap map);
	//�Խñ� ��õ�� ����
	public int getMoreRecommend(Object bno);
	//�Խñ� ��õ�� ����
	public int reduceRecommend(Object bno);
	//��õ���� ��ȸ
	public int checkRecommend(HashMap map);
	//��õ����ϱ�
	public void cancelRecommend(HashMap map);
	//�ֽű� ��������
	public List<GBoardVO> getNewList();
	//����Ʈ�� ��������
	public List<GBoardVO> getBestList();
	
}
