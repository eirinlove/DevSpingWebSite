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
	
	//안 읽은 쪽지 개수
	@Override
	public String countMessage(String userId) {
		return mapper.countMessageView(userId);
	}
	//최신 쪽지 3개 가져오기
	@Override
	public List<MessageVO> readRecentMessages(String userId) {
		return mapper.readRecentMessages(userId);
	}
	//쪽지 전송
	@Override
	public int insert(MessageVO vo) {
		return mapper.insert(vo);
	}
	//보낸쪽지 리스트 가져오기
	@Override
	public List<MessageVO> sentList(String sender) {
		return mapper.sentList(sender);
	}
	//받은 쪽지 리스트 가져오기
	@Override
	public List<MessageVO> receptionList(String receiver) {
		return mapper.receptionList(receiver);
	}
	//쪽지 읽음처리
	@Override
	public int readMessage(long mno) {
		return mapper.readMessage(mno);
	}
	//쪽지 삭제
	@Override
	public int delete(long mno) {
		return mapper.delete(mno);
	}

}
