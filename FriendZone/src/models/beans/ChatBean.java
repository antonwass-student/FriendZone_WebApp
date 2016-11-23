package models.beans;

import bo.ConversationNewBO;
import bo.StreamGetRequestBO;
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

/**
 * Created by chris on 2016-11-23.
 */
@ManagedBean(name = "chat")
@RequestScoped
public class ChatBean {
    public ChatBean() {
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

        return "profile";
    }
}
