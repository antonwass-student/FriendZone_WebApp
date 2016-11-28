package models.beans;

import bo.UserSmallBO;
import bo.WallPostBO;
import bo.WallPostNewBO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import config.FriendConfig;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MediaType;

/**
 * Created by chris on 2016-11-17.
 */

@ManagedBean(name = "post")
public class PostBean
{
    private String message;
    private byte[] picture;

    public PostBean() {}

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public void post(int wallId){
        if (message.length() < 1) return;

        System.out.println("Trying to post...");
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);


        String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);

        WebResource webResource = c.resource(FriendConfig.getFriendApiUrl() + "/wall/post");
        WallPostNewBO post = new WallPostNewBO();
        post.setMessage(message);
        post.setPicture(picture);
        post.setWallId(wallId);
        post.setAuthorSessionId(sessionId);

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, post);

        System.out.println("HTTP: " + response.getStatus());
    }
}
