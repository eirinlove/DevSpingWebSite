package org.yoon.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.yoon.domain.MessageVO;
import org.yoon.mapper.MessageMapper;
import org.springframework.stereotype.Service;
import lombok.Setter;

@Service
public class MessageServiceImpl implements MessageService{

	@Setter(onMethod_= @Autowired)
	private MessageMapper mapper;
	
	//�� ���� ���� ����
	@Override
	public String countMessage(String userId) {
		return mapper.countMessageView(userId);
	}
	//�ֽ� ���� 3�� ��������
	@Override
	public List<MessageVO> readRecentMessages(String userId) {
		return mapper.readRecentMessages(userId);
	}
	//���� ����
	@Override
	public int insert(MessageVO vo) {
		return mapper.insert(vo);
	}
	//�������� ����Ʈ ��������
	@Override
	public List<MessageVO> sentList(String sender) {
		return mapper.sentList(sender);
	}
	//���� ���� ����Ʈ ��������
	@Override
	public List<MessageVO> receptionList(String receiver) {
		return mapper.receptionList(receiver);
	}
	//���� ����ó��
	@Override
	public int readMessage(long mno) {
		return mapper.readMessage(mno);
	}
	//���� ����
	@Override
	public int delete(long mno) {
		return mapper.delete(mno);
	}

}
