package models.beans;

import bo.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import config.FriendConfig;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by chris on 2016-11-23.
 */
@ManagedBean(name = "chat")
@ViewScoped
public class ChatBean {
    private Collection<ConversationBO> conversations;
    private ConversationMessagesBO conversation;
    private UserSmallBO me;
    private int activeConversationId;
    private String messageFromInput;
    private String uid;

    public ChatBean() {
        getConversationsOfUser();
        getLoggedInUser();
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getMessageFromInput() {
        return messageFromInput;
    }

    public void setMessageFromInput(String messageFromInput) {
        this.messageFromInput = messageFromInput;
    }

    public UserSmallBO getMe() {
        return me;
    }

    public void setMe(UserSmallBO me) {
        this.me = me;
    }

    public void setConversations(Collection<ConversationBO> conversations) {
        this.conversations = conversations;
    }

    public Collection<ConversationBO> getConversations() {
        return conversations;
    }

    public ConversationMessagesBO getConversation() {
        return conversation;
    }

    public void setConversation(ConversationMessagesBO conversation) {
        this.conversation = conversation;
    }

    public String redirectToPage(){
        return "new_chat";
    }

    public String createNewConversation(int id){
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);

        WebResource webResource = c.resource(FriendConfig.getChatApiUrl() + "/conversation/create");

        String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        ConversationNewBO conv = new ConversationNewBO();
        conv.setCreator_user_session_id(sessionId);
        conv.setUser_to_invite(id);
        conv.setName("Chat Name");

        ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, conv);

        return "chat";
    }

    public String getConversationsOfUser(){
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);

        String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        WebResource webResource = c.resource(FriendConfig.getChatApiUrl() + "/conversation/get/"+sessionId);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        ConversationListBO list = response.getEntity(ConversationListBO.class);
        conversations = list.getConvos();

        for (ConversationBO con : conversations){
            String name = "";
            for (UserSmallBO usr : con.getMembers()){
                name += usr.getName() + ", ";
            }
            name = name.substring(0,name.lastIndexOf(","));
            con.setTitle(name);
        }

        return "";
    }
    public void getLoggedInUser(){
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);
        String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        WebResource webResource = c.resource(FriendConfig.getFriendApiUrl() + "/user/get/session/"+sessionId);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        me = response.getEntity(UserSmallBO.class);
    }
    public void grabChat(int convId){
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);
        String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        WebResource webResource = c.resource(FriendConfig.getChatApiUrl() + "/conversation/messages");
        ConversationRequestBO convReq = new ConversationRequestBO();
        convReq.setRequested_conversation_id(convId);
        convReq.setUser_session_id(sessionId);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,convReq);
        conversation = response.getEntity(ConversationMessagesBO.class);
        activeConversationId = convId;

    }

    public void sendMessage(){
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);
        String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        WebResource webResource = c.resource(FriendConfig.getChatApiUrl() + "/conversation/send");
        MessageNewBO msg = new MessageNewBO();
        msg.setSender_session_ID(sessionId);
        msg.setTarget_conversation_ID(activeConversationId);
        msg.setText(messageFromInput);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,msg);
        System.out.println("Send response: "+response.getStatus());
        MessageSentResponseBO resp = response.getEntity(MessageSentResponseBO.class);
        System.out.println();
        Push.sendToSessions(resp,messageFromInput);
        messageFromInput = "";

    }



}
