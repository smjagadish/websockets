package org.websocketHandlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class Handler extends TextWebSocketHandler {
    private static Logger logger = LoggerFactory.getLogger(Handler.class);
    private WebSocketSession session = null;
    private CopyOnWriteArrayList<WebSocketSession> sessionList = new CopyOnWriteArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.session = session;

        sessionList.add(session);
        super.afterConnectionEstablished(session);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        logger.info("recieved message from client");
        String msg = message.getPayload();
        logger.info(" the data sent is :" + msg);
        logger.info("streaming back to client");
        String data = "all good, will start streaming to you";
        session.sendMessage(new TextMessage("here is the response:" + data));

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        sessionList.remove(session);
        super.afterConnectionClosed(session, status);
    }

    public CopyOnWriteArrayList<WebSocketSession> getSession() {
        return sessionList;
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (sessionList.contains(session)) {
            sessionList.remove(session);
        }
        super.handleTransportError(session, exception);
    }
}
