package models.beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 * Created by Anton on 2016-11-16.
 */
@ManagedBean
@SessionScoped
public class LoginBean {
    private String username;
    private String password;

    public LoginBean(){}

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String login() {
        return "";
    }
}
