package com.wgx.threekingdomskills.websocket;

import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author:wgx
 * version:1.0
 */
@ServerEndpoint(value = "/tkk/waitGameEnd", configurator = GetHttpSessionConfig.class)
@Component
public class WaitGameEnd {
    private static Map<String, List<WaitGameEnd>> gameMembers = new ConcurrentHashMap<>();

    private Session session;

    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws EncodeException, IOException {
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        String roomId = (String) httpSession.getAttribute("roomId");
        List<WaitGameEnd> waitGameEndMembers = gameMembers.get(roomId);
        if (waitGameEndMembers == null) {
            ArrayList<WaitGameEnd> newGameMembers = new ArrayList<>();
            newGameMembers.add(this);
            gameMembers.put(roomId, newGameMembers);
        } else {
            waitGameEndMembers.add(this);
            gameMembers.put(roomId, waitGameEndMembers);
        }
    }
    @OnMessage
    public void onMessage(String message, Session session) throws EncodeException, IOException {
        if (message.equals("endGame")) {
            String roomId = (String) httpSession.getAttribute("roomId");
            List<WaitGameEnd> waitGameEndMembers = gameMembers.get(roomId);
            for (WaitGameEnd waitGameEndMember : waitGameEndMembers) {
                waitGameEndMember.session.getBasicRemote().sendText("endGame");
            }
        }
    }
    @OnClose
    public void onClose() {
        String roomId = (String) httpSession.getAttribute("roomId");
        List<WaitGameEnd> waitGameEndMembers = gameMembers.get(roomId);
        waitGameEndMembers.remove(this);
    }
}
