package models.beans;

import bo.RegisterBO;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import config.FriendConfig;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by chris on 2016-11-16.
 */
@ManagedBean(name = "register")
@SessionScoped
public class RegisterBean {

    private String name;
    private String password;
    private String rePassword;
    private String email;

    public RegisterBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRePassword() {
        return rePassword;
    }

    public void setRePassword(String rePassword) {
        this.rePassword = rePassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void register(){

        if (!password.equals(rePassword) || name.length() < 3 || email.length() < 3 ){return;}

        RegisterBO user = new RegisterBO();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client c = Client.create(clientConfig);

        WebResource webResource = c.resource(FriendConfig.getFriendApiUrl() + "/user/register");

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, user);

        if (response.getStatus() != 200){
            System.out.println("HTTP code: " + response.getStatus());
        }

        String resp = response.getEntity(String.class);
        System.out.println("response: " + resp.toString());
        if (resp.equals("Registerd")){
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/profile.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
