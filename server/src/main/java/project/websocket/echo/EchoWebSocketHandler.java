package project.websocket.echo;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import project.websocket.dao.ConnectionRepository;

public class EchoWebSocketHandler extends AbstractWebSocketHandler {

	private static Logger logger = LoggerFactory.getLogger(EchoWebSocketHandler.class);

	@Autowired
	ConnectionRepository connections;

	HashSet<WebSocketSession> sessions;

	@Autowired
	public EchoWebSocketHandler() {
		sessions = new HashSet<WebSocketSession>();
	}

	@Override
	public void afterConnectionEstablished(WebSocketSession session) {
		logger.debug("Opened new session in instance " + this);
		sessions.add(session);
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		sessions.remove(session);
	}

	@Override
	public void handleBinaryMessage(WebSocketSession session, BinaryMessage message)
			throws Exception {
		
		byte[] echoMessage = message.getPayload().array();

		for (WebSocketSession webSocketSession : sessions) {
			webSocketSession.sendMessage(new BinaryMessage(echoMessage));
		}

	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		
		for (WebSocketSession webSocketSession : sessions) {
			webSocketSession.sendMessage( new TextMessage(message.getPayload()));
		}
		
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception)
			throws Exception {
		session.close(CloseStatus.SERVER_ERROR);
	}

}