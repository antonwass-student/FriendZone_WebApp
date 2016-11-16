package models.bo;

/**
 * Created by Anton on 2016-11-16.
 */
public class LoginBO {
    private String email;
    private String password;

    public LoginBO(String arg){}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
