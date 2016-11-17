package bo;

/**
 * Created by Anton on 2016-11-16.
 */
public class UserSmallBO {
    private int id;
    public String name;
    public String mail;

    public UserSmallBO(String arg){}
    public UserSmallBO(){}

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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
}
