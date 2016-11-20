package models.beans;

import bo.UserSmallBO;
import bo.WallBO;
import bo.WallPostBO;
import bo.WallPostNewBO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import javafx.event.ActionEvent;

import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.Map;

/**
 * Created by chris on 2016-11-17.
 */
@ManagedBean(name = "profile")
@ViewScoped
public class ProfileBean {
    private String name;
    private String email;
    private int id;
    private WallBO wall;
    private int wallId;
    private Collection<WallPostBO> posts;


    private String userId;

    public ProfileBean() {
        Map<String,String> params =
                FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        userId = params.get("userId");
        System.out.println(userId);
        if (userId == null) {getMe();}
        else{id = Integer.valueOf(userId);}
        getProfile();
        getWall();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setWall(WallBO wall) {
        this.wall = wall;
    }

    public int getWallId() {
        return wallId;
    }

    public void setWallId(int wallId) {
        this.wallId = wallId;
    }

    public Collection<WallPostBO> getPosts() {
        return posts;
    }

    public void setPosts(Collection<WallPostBO> posts) {
        this.posts = posts;
    }

    public void getProfile(){


        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client c = Client.create(clientConfig);
        WebResource webResource = c.resource("http://localhost:8080/api/user/get/"+id);

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        UserSmallBO user = response.getEntity(UserSmallBO.class);

        if (user == null){
            System.out.println("Didn't get user by session.");
        }else{
            this.name = user.getName();
            this.email = user.getMail();
            this.id = user.getId();

        }
    }

    public void getWall(){
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client c = Client.create(clientConfig);
        WebResource webResource = c.resource("http://localhost:8080/api/wall/get/"+id);

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        this.wall = response.getEntity(WallBO.class);
        wallId = wall.getId();
        posts = wall.getPosts();


    }

    public void getMe(){
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client c = Client.create(clientConfig);
        WebResource webResource = c.resource("http://localhost:8080/api/user/get/session/"+FacesContext.getCurrentInstance().getExternalContext().getSessionId(false));

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        UserSmallBO user = response.getEntity(UserSmallBO.class);
        id = user.getId();
    }

    public void attributeListener(javax.faces.event.ActionEvent event){
         id = (Integer)event.getComponent().getAttributes().get("userId");
        getProfile();
        getWall();
    }
}
