package bo;

/**
 * Created by chris on 2016-11-16.
 */
public class RegisterBO {
    private String name;
    private String email;
    private String password;

    public RegisterBO() {}

    public RegisterBO(String arg) {}

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
