package project.websocket.controller;

import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import project.websocket.dao.ConnectionRepository;
import project.websocket.dao.MetadataRepository;

public class AdminHandler extends AbstractWebSocketHandler {

	@Autowired
	ConnectionRepository connections;

	@Autowired
	MetadataRepository metadataRepo;

	public static HashSet<WebSocketSession> sessions;

	final String dateFormat = "EEE, yyyy-MM-dd HH:mm:ss";

	@Autowired
	public AdminHandler() {
		sessions = new HashSet<WebSocketSession>();
	}

	@Override
	@Transactional
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		sessions.add(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}
}
