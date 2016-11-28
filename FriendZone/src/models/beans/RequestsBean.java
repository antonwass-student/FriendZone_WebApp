package models.beans;

import bo.FriendRequestBO;
import bo.FriendRequestDecisionBO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import config.FriendConfig;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by chris on 2016-11-18.
 */
@ManagedBean(name = "requests")
@RequestScoped
public class RequestsBean {
    private Collection<FriendRequestBO> requests;

    public RequestsBean() { getFriendRequests(); }

    public Collection<FriendRequestBO> getRequests() {
        return requests;
    }

    public void setRequests(Collection<FriendRequestBO> requests) {
        this.requests = requests;
    }

    public void getFriendRequests(){
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client c = Client.create(clientConfig);
        String sid = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        WebResource webResource = c.resource(FriendConfig.getFriendApiUrl() + "/friend/request/get/"+sid);
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        requests = response.getEntity(new GenericType<Collection<FriendRequestBO>>(){});
    }

    public void answerFriendRequest(int reqId, boolean accept){

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);



        FriendRequestDecisionBO decision = new FriendRequestDecisionBO();
        String sid = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        decision.setDecision(accept);
        decision.setReceiver_session_id(sid);
        decision.setRequest_id(reqId);

        WebResource webResource = c.resource(FriendConfig.getFriendApiUrl() + "/friend/request/decision");
        ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN).type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, decision);


        String answer = response.getEntity(String.class);
        System.out.println("HTTP: " + response.getStatus());
        System.out.println(answer);

    }


}
