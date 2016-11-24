package models.beans;

import bo.*;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by chris on 2016-11-23.
 */
@ManagedBean(name = "chat")
@SessionScoped
public class ChatBean {
    private Collection<ConversationBO> conversations;
    private ConversationMessagesBO conversation;

    public ChatBean() { getConversationsOfUser();
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

        WebResource webResource = c.resource("http://localhost:8080/chat-api/conversation/create");

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
        WebResource webResource = c.resource("http://localhost:8080/chat-api/conversation/get/"+sessionId);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        ConversationListBO list = response.getEntity(ConversationListBO.class);
        conversations = list.getConvos();
        return "";
    }

    public void grabChat(int convId){
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);
        String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        WebResource webResource = c.resource("http://localhost:8080/chat-api/conversation/messages");
        ConversationRequestBO convReq = new ConversationRequestBO();
        convReq.setRequested_conversation_id(convId);
        convReq.setUser_session_id(sessionId);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,convReq);
        conversation = response.getEntity(ConversationMessagesBO.class);
    }



}
