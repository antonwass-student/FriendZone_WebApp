package models.beans;

import bo.RegisterBO;

import javax.faces.bean.ManagedBean;

/**
 * Created by chris on 2016-11-16.
 */
@ManagedBean(name = "register")
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


    }
}
