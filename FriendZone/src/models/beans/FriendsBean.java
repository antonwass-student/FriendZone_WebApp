package models.beans;

import bo.FriendRequestBO;
import bo.FriendshipDeleteBO;
import bo.UserSmallBO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import config.FriendConfig;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by chris on 2016-11-20.
 */
@ManagedBean(name = "friends")
public class FriendsBean {
    private Collection<UserSmallBO> friends;
    private int noOfFriends;

    public FriendsBean() { getUserFriends();}

    public int getNoOfFriends() {
        return noOfFriends;
    }

    public void setNoOfFriends(int noOfFriends) {
        this.noOfFriends = noOfFriends;
    }

    public Collection<UserSmallBO> getFriends() {
        return friends;
    }

    public void setFriends(Collection<UserSmallBO> friends) {
        this.friends = friends;
    }

    public void getUserFriends(){
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client c = Client.create(clientConfig);
        String sid = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        WebResource webResource = c.resource(FriendConfig.getFriendApiUrl() + "/friend/friends/get/"+sid);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        friends = response.getEntity(new GenericType<Collection<UserSmallBO>>(){});
        noOfFriends = friends.size();
    }

    public void removeFriend(int userIdToRemove){
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client c = Client.create(clientConfig);
        String sid = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        WebResource webResource = c.resource(FriendConfig.getFriendApiUrl() + "/friend/remove");

        FriendshipDeleteBO fdel = new FriendshipDeleteBO();
        fdel.setUser_session_id(sid);
        fdel.setFriendshipId(userIdToRemove);
        ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,fdel);
        System.out.println("HTTP status: " + response.getStatus());
    }
}
