package org.yoon.service;

import java.util.List;

import org.yoon.domain.BoardAttachVO;
import org.yoon.domain.BoardVO;
import org.yoon.domain.GBoardVO;
import org.yoon.domain.MemberAttachVO;
import org.yoon.domain.MemberVO;

public interface MemberService {
	
	//id�ߺ�üũ
	public int idCheck(String userid);
	
	//ȸ�� ���
	public void insert(MemberVO vo);
	
	//ȸ�� ���� ��������
	public MemberVO read(String userid);
	//ȸ�� ���� ����
	public int update(MemberVO vo);
	//ȸ�� ������ �������� ��������
	public List<BoardAttachVO>getAttachList(String userid);
	
}
