package bo;

import java.sql.Timestamp;

/**
 * Created by chris on 2016-11-18.
 */
public class WallPostNewBO {
    private int id;
    private String message;
    private Timestamp timestamp;
    private String authorSessionId;
    private byte[] picture; //??
    private int wallId;

    public int getWallId() {
        return wallId;
    }

    public void setWallId(int wallId) {
        this.wallId = wallId;
    }

    public WallPostNewBO() {}

    public WallPostNewBO(String args) {}

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

    public String getAuthorSessionId() {
        return authorSessionId;
    }

    public void setAuthorSessionId(String authorSessionId) {
        this.authorSessionId = authorSessionId;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }
}
