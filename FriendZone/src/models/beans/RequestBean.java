package models.beans;

import bo.FriendRequestBO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MediaType;

/**
 * Created by chris on 2016-11-18.
 */
@ManagedBean(name = "request")
public class RequestBean {

    public void sendRequestTo(int userId){
        FriendRequestBO request = new FriendRequestBO();


        String sid = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client c = Client.create(clientConfig);

        WebResource webResource = c.resource("http://localhost:8080/api/request/send");

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, request);

        String resp = response.getEntity(String.class);

        // lite checkar.
    }

}
