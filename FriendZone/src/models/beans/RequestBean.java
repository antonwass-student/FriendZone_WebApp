package models.beans;

import bo.FriendRequestBO;
import bo.FriendRequestNewBO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import config.FriendConfig;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MediaType;

/**
 * Created by chris on 2016-11-18.
 */
@ManagedBean(name = "req")
@SessionScoped
public class RequestBean {

    public RequestBean() {
    }

    public void sendRequestTo(int userId){
        FriendRequestNewBO request = new FriendRequestNewBO();

        String sid = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        request.setReceiver_user_id(userId);
        request.setSender_sid(sid);
        request.setMessage("Hej, jag vill lägga till dig som vän.");

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);

        WebResource webResource = c.resource(FriendConfig.getFriendApiUrl() + "/friend/request/send");
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, request);

        String resp = response.getEntity(String.class);

        // lite checkar.
    }

}
