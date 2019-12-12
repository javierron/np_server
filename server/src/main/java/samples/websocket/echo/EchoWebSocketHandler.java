/*
 * Copyright 2012-2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package samples.websocket.echo;

import java.util.HashSet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.BinaryMessage;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.AbstractWebSocketHandler;

/**
 * Echo messages by implementing a Spring {@link WebSocketHandler} abstraction.
 */
public class EchoWebSocketHandler extends AbstractWebSocketHandler {

	private static Logger logger = LoggerFactory.getLogger(EchoWebSocketHandler.class);

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
