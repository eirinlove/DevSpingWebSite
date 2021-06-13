package org.yoon.socket;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.util.StringUtils;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import org.yoon.domain.MemberVO;
import org.yoon.mapper.GBoardMapper;
import org.yoon.mapper.MessageMapper;
import org.yoon.security.domain.CustomUser;
import org.yoon.service.GBoardService;
import org.yoon.service.MessageService;

import lombok.Setter;

public class EchoHandler extends TextWebSocketHandler{
	List<WebSocketSession> sessions = new ArrayList<>();
	
	@Autowired
	private MessageService service;
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception{
		sessions.add(session);
//		userSessions.put(senderId, session);
	}
	
	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception{
		System.out.println("handleTextMessage:"+session+" : "+message);
		
			String userId = message.getPayload();
			session.sendMessage(new TextMessage("recMsg:"+service.countMessage(message.getPayload())+":"+service.readRecentMessages(message.getPayload())));
		
	}
	
	private String getId(WebSocketSession session) {
		SecurityContext context= (SecurityContext)session.getAttributes().get(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);
		
		CustomUser user = (CustomUser)context.getAuthentication().getPrincipal();
		if(user==null) {
			return session.getId();
		}else {
			
			return user.getUsername();
		}
	}
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("afterConnectionClosed"+session+":"+status);
		
	}
	

}
