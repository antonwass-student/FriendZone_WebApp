package models.beans;

import bo.LoginBO;
import bo.LoginResponseBO;
import com.sun.deploy.util.SessionState;
import com.sun.faces.application.resource.ClientResourceInfo;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

/**
 * Created by Anton on 2016-11-16.
 */
@ManagedBean(name = "login")
@SessionScoped
public class LoginBean {
    private String email;
    private String password;
    private int test;

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

        Client c = Client.create();

        WebResource webResource = c.resource("http://localhost:8080/api/user/login");

        ClientResponse response = webResource.type("application/json")
                .post(ClientResponse.class,user);

        if (response.getStatus() != 201){
            System.out.println("Error " + response.getStatus());
        }

        LoginResponseBO loginResp = response.getEntity(LoginResponseBO.class);



        // Skicka iväg
        System.out.println(loginResp.isLoggedIn());
        // Få respons?

        return "Inloggad";
    }
}
