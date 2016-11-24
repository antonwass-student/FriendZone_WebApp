package models.beans;

import bo.MessageSentResponseBO;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Anton on 2016-11-23.
 */
@ServerEndpoint("/rtchat/{clientId}")
public class Push {

    private static final ConcurrentHashMap<String, Session> SESSIONS = new ConcurrentHashMap();

    private volatile String clientId;

    @OnOpen
    public void onOpen(@PathParam("clientId")String clientId, Session session){
        System.out.println(session.getId() + " has opened a connection.");

        this.clientId = clientId;

        SESSIONS.put(clientId, session);

        try{
            session.getBasicRemote().sendText("Connection established");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @OnClose
    public void onClose(Session session){
        SESSIONS.remove(clientId);
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
