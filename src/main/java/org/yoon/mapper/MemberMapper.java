package org.yoon.mapper;

import org.yoon.domain.BoardVO;
import org.yoon.domain.MemberVO;

public interface MemberMapper {

	//1:N����
	public MemberVO read(String userid);
	
	//id�ߺ�üũ
	public int idChk(String userid);
	
	//ȸ������
	public void insert(MemberVO vo);
}
