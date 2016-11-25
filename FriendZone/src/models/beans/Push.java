package models.beans;

import bo.MessageSentResponseBO;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import javax.websocket.server.ServerEndpointConfig;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Anton on 2016-11-23.
 */
@ServerEndpoint(value="/rtchat", configurator = ServletAwareConfig.class)
public class Push {

    private static final ConcurrentHashMap<String, Session> SESSIONS = new ConcurrentHashMap();


    private EndpointConfig config;

    private String sessionId;

    @OnOpen
    public void onOpen(Session session, EndpointConfig config){
        this.config = config;
        sessionId = config.getUserProperties().get("sessionId").toString();


        System.out.println(session.getId() + " has opened a connection.");



        SESSIONS.put(sessionId, session);

        try{
            session.getBasicRemote().sendText("Connection established");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session){
        SESSIONS.remove(sessionId);
    }

    @OnMessage
    public void onMessage(String message){
        System.out.println("Client sent: " + message);
    }

    public static void sendToSessions(MessageSentResponseBO convData, String text){

        synchronized(SESSIONS){
            for(String s : convData.getSession_ids_in_convo()){
                try{
                    SESSIONS.get(s).getBasicRemote().sendText(convData.getSender().getName() + ": " + text);
                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}

