package org.yoon.mapper;

import java.util.List;

import org.yoon.domain.MessageVO;

public interface MessageMapper {
	//�� ���� ���� ���� 
	public String countMessageView(String userId);
	//�ֱ� ���� 3�� ��������
	public List<MessageVO> readRecentMessages(String userId);
	//���� ������
	public int insert(MessageVO vo);
	//���� ������ ����Ʈ ��������
	public List<MessageVO> receptionList(String receiver);
	//���� ������ ����Ʈ ��������
	public List<MessageVO> sentList(String sender);
	//���� ����ó��
	public int readMessage(long mno);
	//���� ����
	public int delete(long mno);
}
