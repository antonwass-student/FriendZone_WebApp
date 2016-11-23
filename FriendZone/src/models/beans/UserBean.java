package models.beans;

import bo.UserSmallBO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.GenericType;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by chris on 2016-11-17.
 */
@ManagedBean(name = "users")
@SessionScoped
public class UserBean {
    private int id;
    private String name;
    private String email;
    private Collection<UserSmallBO> users;
    private Collection<UserSmallBO> result;

    public UserBean() {getResults();}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Collection<UserSmallBO> getResult() {
        return result;
    }

    public void setResult(Collection<UserSmallBO> result) {
        this.result = result;
    }

    public void getResults(){


        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);
        Client c = Client.create(clientConfig);


        WebResource webResource = c.resource("http://localhost:8080/api/user/get/all");
        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).get(ClientResponse.class);
        Collection<UserSmallBO> usrs = response.getEntity(new GenericType<Collection<UserSmallBO>>(){});

        users = usrs;
        System.out.println(users);
    }

    public void getBySearchInput(ValueChangeEvent vce){

        result = new ArrayList<UserSmallBO>();
        name = (String)vce.getNewValue();
        for (UserSmallBO u: users) {
            if (u.name.toUpperCase().contains(name.toUpperCase())){
                result.add(u);
            }
        }

    }

    public void searchStringChanged(ValueChangeEvent vce){

    }
}
