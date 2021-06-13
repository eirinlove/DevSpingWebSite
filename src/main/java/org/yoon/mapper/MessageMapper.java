package org.yoon.mapper;

import java.util.List;

import org.yoon.domain.MessageVO;

public interface MessageMapper {
	//안 읽은 쪽지 개수 
	public String countMessageView(String userId);
	//최근 쪽지 3개 가져오기
	public List<MessageVO> readRecentMessages(String userId);
	//쪽지 보내기
	public int insert(MessageVO vo);
	//받은 쪽지함 리스트 가져오기
	public List<MessageVO> receptionList(String receiver);
	//보낸 쪽지함 리스트 가져오기
	public List<MessageVO> sentList(String sender);
	//쪽지 읽음처리
	public int readMessage(long mno);
	//쪽지 삭제
	public int delete(long mno);
}
