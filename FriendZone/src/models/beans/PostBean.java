package models.beans;

import bo.UserSmallBO;
import bo.WallPostBO;
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

    public void post(){
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);


        String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        WebResource webResource = c.resource("http://localhost:8080/api/user/get/session/"+sessionId);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        UserSmallBO author = new UserSmallBO();
        author = response.getEntity(UserSmallBO.class);
        System.out.println("HTTP: " + response.getStatus());

        webResource = c.resource("http://localhost:8080/api/wall/post");
        WallPostBO post = new WallPostBO();
        post.setMessage(message);
        post.setPicture(picture);
        post.setAuthor(author);
        response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, post);

        System.out.println("HTTP: " + response.getStatus());
    }
}
