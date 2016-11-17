package bo;

/**
 * Created by Anton on 2016-11-16.
 */
public class LoginBO {
    private String email;
    private String password;
    private String session_id;

    public LoginBO(String arg){}
    public LoginBO(){}

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

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
