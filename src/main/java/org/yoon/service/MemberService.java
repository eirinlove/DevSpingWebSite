package org.yoon.service;

import org.yoon.domain.BoardVO;
import org.yoon.domain.MemberVO;

public interface MemberService {
	
	//id�ߺ�üũ
	public int idCheck(String userid);
	
	//ȸ�� ���
	public void insert(MemberVO vo);

}
