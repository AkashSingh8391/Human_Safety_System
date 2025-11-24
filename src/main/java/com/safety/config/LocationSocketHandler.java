package com.safety.config;

import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.util.*;

public class LocationSocketHandler extends TextWebSocketHandler {

    private static final List<WebSocketSession> policeSessions = new ArrayList<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        policeSessions.add(session);
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage msg) throws Exception {
        for (WebSocketSession s : policeSessions) {
            if (s.isOpen()) {
                s.sendMessage(msg);
            }
        }
    }
}
