package project.websocket.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

import project.websocket.dao.ConnectionRepository;
import project.websocket.dao.MetadataRepository;
import project.websocket.model.Connection;
import project.websocket.model.Metadata;

public class WeShareHandler extends AbstractWebSocketHandler {

	public class AdminInfo {

		int messages;
		int connections;
		int currentConnections;

		public AdminInfo(int messages, int connections, int currentConnections){
			this.messages = messages;
			this.connections = connections;
			this.currentConnections = currentConnections;
		}
	}

	@Autowired
	ConnectionRepository connections;

	@Autowired
	MetadataRepository metadataRepo;

	public static HashSet<WebSocketSession> sessions;

	final String dateFormat = "EEE, yyyy-MM-dd HH:mm:ss";

	@Autowired
	public WeShareHandler() {
		sessions = new HashSet<WebSocketSession>();
	}

	@Override
	@Transactional
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("ADD CONNECTION");
		sessions.add(session);

		Date dt = new java.util.Date();
		SimpleDateFormat sdf = new java.text.SimpleDateFormat(dateFormat);
		String currentTime = sdf.format(dt);

		Connection conn = new Connection(currentTime);
		connections.saveAndFlush(conn);

		List<Metadata> lastMessages = metadataRepo.findFirst10ByOrderByMsgTimeAsc();

		Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();

		for (Metadata metadata : lastMessages) {
			Message m = new Message(metadata);
			session.sendMessage(new TextMessage( gson.toJson(m)));
		}

		updateAdmin();
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
		System.out.println("REMOVE CONNECTION " + status);
		sessions.remove(session);
		updateAdmin();
	}

	@Override
	public void handleBinaryMessage(WebSocketSession session, BinaryMessage message)
			throws Exception {
		
		byte[] echoMessage = message.getPayload().array();

		System.out.println("CONNECTION NUMBER: " + sessions.size());

		for (WebSocketSession webSocketSession : sessions) {
			System.out.println("SEND MESSAGE");
			webSocketSession.sendMessage(new BinaryMessage(echoMessage));
		}

	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println("CONNECTION NUMBER: " + sessions.size());

		String payload = message.getPayload();

		Gson gson = new GsonBuilder().setDateFormat(dateFormat).create();
		Message msg = gson.fromJson(payload, Message.class);
		System.out.println(msg.getLocation());
		metadataRepo.saveAndFlush(new Metadata(msg));

		for (WebSocketSession webSocketSession : sessions) {
			System.out.println("SEND MESSAGE");
			webSocketSession.sendMessage( new TextMessage(message.getPayload()));
		}
		
		updateAdmin();
	}

	@Override
	public void handleTransportError(WebSocketSession session, Throwable exception)
			throws Exception {
		session.close(CloseStatus.SERVER_ERROR);
	}

	void updateAdmin() throws Exception {

		int all = (int)metadataRepo.count();
        int all_con = (int)connections.count();
        int current_connections = WeShareHandler.sessions.size();

		AdminInfo data = new AdminInfo(all, all_con, current_connections);

		Gson gson = new GsonBuilder().create();
		String info = gson.toJson(data);

		for (WebSocketSession webSocketSession : AdminHandler.sessions) {
			webSocketSession.sendMessage(new TextMessage(info));
		}
	}

}
