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
@ServerEndpoint(value = "/tkk/waitGame", configurator = GetHttpSessionConfig.class)
@Component
public class WaitGame {
    private static Map<String, List<WaitGame>> roomMembers = new ConcurrentHashMap<>();

    private Session session;

    private HttpSession httpSession;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config) throws EncodeException, IOException {
        System.out.println("connection");
        this.session = session;
        HttpSession httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        this.httpSession = httpSession;
        String roomId = (String) httpSession.getAttribute("roomId");
        List<WaitGame> waitGameMembers = roomMembers.get(roomId);
        if (waitGameMembers == null) {
            ArrayList<WaitGame> newWaitGameMembers = new ArrayList<>();
            newWaitGameMembers.add(this);
            roomMembers.put(roomId, newWaitGameMembers);
        } else {
            //发送新的当前的人数
            for (WaitGame waitGameMember : waitGameMembers) {
                waitGameMember.session.getBasicRemote().sendText("flushCurrentMembers");
            }
            waitGameMembers.add(this);
            roomMembers.put(roomId, waitGameMembers);
        }
    }

    @OnMessage
    public void onMessage(String message, Session session) throws EncodeException, IOException {
        System.out.println(message);
        String roomId = (String) httpSession.getAttribute("roomId");
        System.out.println(roomId);
        List<WaitGame> waitGameMembers = roomMembers.get(roomId);
        if (message.equals("quit")) {
            if (waitGameMembers.size() > 1) {
                waitGameMembers.remove(this);
                roomMembers.put(roomId, waitGameMembers);
                for (WaitGame waitGameMember : waitGameMembers) {
                    waitGameMember.session.getBasicRemote().sendText("flushCurrentMembers");
                }
            } else {
                roomMembers.remove(roomId);
            }
        } else {
            for (WaitGame waitGameMember : waitGameMembers) {
                waitGameMember.session.getAsyncRemote().sendText(message);
            }
        }
    }
    @OnClose
    public void onClose() {
        System.out.println("close");
        String roomId = (String) httpSession.getAttribute("roomId");
        List<WaitGame> waitGameMembers = roomMembers.get(roomId);
        waitGameMembers.remove(this);
    }

}
