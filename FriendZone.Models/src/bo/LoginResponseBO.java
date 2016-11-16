package bo;

/**
 * Created by chris on 2016-11-16.
 */
public class LoginResponseBO {
    private boolean loggedIn;

    public LoginResponseBO(){}

    public LoginResponseBO(String args) {}

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
