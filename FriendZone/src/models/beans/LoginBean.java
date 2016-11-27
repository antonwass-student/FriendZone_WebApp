package models.beans;

import bo.LoginBO;
import bo.LoginResponseBO;
import com.sun.deploy.util.SessionState;
import com.sun.faces.application.resource.ClientResourceInfo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import config.FriendConfig;

import java.io.IOException;

/**
 * Created by Anton on 2016-11-16.
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginBean {
    private String email;
    private String password;
    private int test;
    private String loginStatus;


    public String getLoginStatus() {
        return loginStatus;
    }

    public void setLoginStatus(String loginStatus) {
        this.loginStatus = loginStatus;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public LoginBean(){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        //
        LoginBO user = new LoginBO();
        user.setEmail(email);
        user.setPassword(password);
        user.setSession_id(FacesContext.getCurrentInstance().getExternalContext().getSessionId(false));

        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client c = Client.create(clientConfig);

        WebResource webResource = c.resource(FriendConfig.getFriendApiUrl() + "/api/user/login");

        ClientResponse response = webResource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON)
                .post(ClientResponse.class, user);

        if (response.getStatus() != 200){
            System.out.println("Error " + response.getStatus());
        }

        LoginResponseBO loginResp = response.getEntity(LoginResponseBO.class);

        System.out.println("Recieved: " + loginResp.isLoggedIn());

        if (loginResp.isLoggedIn()){
                return "stream.xhtml";
        }else{
            loginStatus = "Couldn't login.";
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect("/login.xhtml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }

    public String logout(){

        String sessionId = FacesContext.getCurrentInstance().getExternalContext().getSessionId(false);
        ClientConfig clientConfig = new DefaultClientConfig();
        clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING, Boolean.TRUE);

        Client c = Client.create(clientConfig);

        WebResource webResource = c.resource(FriendConfig.getFriendApiUrl() + "/api/user/logout/"+sessionId);

        ClientResponse response = webResource.accept(MediaType.TEXT_PLAIN)
                .post(ClientResponse.class);

        System.out.println("User logged out.");

        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

        return "login";
    }
}
