package models.beans;

import bo.StreamGetRequestBO;
import bo.StreamGetResponseBO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MediaType;
import java.util.Collection;

/**
 * Created by chris on 2016-11-18.
 */
@ManagedBean(name = "stream")
@ViewScoped
public class StreamBean {
    private StreamGetResponseBO stream;

    public StreamBean() { getUserStream(); }

    public StreamGetResponseBO getStream() {
        return stream;
    }

    public void setStream(StreamGetResponseBO stream) {
        this.stream = stream;
    }

    public void getUserStream(){
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client c = Client.create(clientConfig);
        String sid = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        WebResource webResource = c.resource("http://localhost:8080/api/stream/load");

        StreamGetRequestBO streamRequest = new StreamGetRequestBO();
        streamRequest.setUserSessionId(sid);
        streamRequest.setNumOfPosts(10);
        streamRequest.setPostsFromDate(null);

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).post(ClientResponse.class,streamRequest);
        stream = response.getEntity(StreamGetResponseBO.class);
        System.out.println(stream.getPosts().size());
    }
}
