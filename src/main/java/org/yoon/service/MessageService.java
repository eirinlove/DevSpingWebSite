package org.yoon.service;

import java.util.List;

import org.yoon.domain.MessageVO;

public interface MessageService {

	//안 읽은 쪽지 개수
	public String countMessage(String userId);
	//최근 쪽지 3개
	public List<MessageVO> readRecentMessages(String userId);
	//쪽지 전송
	public int insert(MessageVO vo);
	//보낸쪽지리스트
	public List<MessageVO> sentList(String sender);
	//받은쪽지리스트
	public List<MessageVO> receptionList(String receiver);
	//쪽지 읽음처리
	public int readMessage(long mno);
	//쪽지 삭제
	public int delete(long mno);
}
