package org.yoon.service;

import java.util.List;

import org.yoon.domain.MessageVO;

public interface MessageService {

	//�� ���� ���� ����
	public String countMessage(String userId);
	//�ֱ� ���� 3��
	public List<MessageVO> readRecentMessages(String userId);
	//���� ����
	public int insert(MessageVO vo);
	//������������Ʈ
	public List<MessageVO> sentList(String sender);
	//������������Ʈ
	public List<MessageVO> receptionList(String receiver);
	//���� ����ó��
	public int readMessage(long mno);
	//���� ����
	public int delete(long mno);
}
