package bo;

import java.sql.Timestamp;

/**
 * Created by Anton on 2016-11-17.
 */
public class WallPostBO {
    private int id;
    private String message;
    private Timestamp timestamp;
    private UserSmallBO author;
    private byte[] picture; //??

    public WallPostBO(String arg){}

    public WallPostBO(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public UserSmallBO getAuthor() {
        return author;
    }

    public void setAuthor(UserSmallBO author) {
        this.author = author;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
