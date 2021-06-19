package org.yoon.service;

import java.util.HashMap;
import java.util.List;

import org.yoon.domain.Criteria;
import org.yoon.domain.GBoardAttachVO;
import org.yoon.domain.GBoardVO;


public interface GBoardService {

	//�Խñ� ���
	public void register(GBoardVO gvo);
	//�Խñ� �󼼺���
	public GBoardVO get(long gno);
	//�Խñ� ����
	public boolean modify(GBoardVO gvo);
	//�Խñ� ����
	public boolean delete(long gno);
	//�Խñ� ��� ��ȸ
	public List<GBoardVO> getList(Criteria cri);
	//����¡�� ���� �� ��ü ����
	public int getTotal(Criteria cri);
	//�۹�ȣ�� ÷������ ����Ʈ ��ȸ
	public List<GBoardAttachVO> getAttachList(Long gno);
	//��õ�ϱ�
	public void recommend(HashMap map);
	//��õ���� ��ȸ
	public int checkRecommend(HashMap map);
	//��õ ���
	public void cancelRecommend(HashMap map);
	//�ֽű� ��ȸ
	public List<GBoardVO> getNewList();
	//����Ʈ�� ��ȸ
	public List<GBoardVO> getBestList();
}