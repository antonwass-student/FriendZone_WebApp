package entities;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created by Anton on 2016-11-15.
 */
@Entity
@Table(name = "WallPost", schema = "dbo", catalog = "community")
public class WallPostEntity {
    private int wallpostId;
    private String message;
    private Timestamp timestamp;
    private byte[] picture;
    private WallEntity wallByWall;
    private UserEntity userByAuthor;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wallpost_id", nullable = false)
    public int getWallpostId() {
        return wallpostId;
    }

    public void setWallpostId(int wallpostId) {
        this.wallpostId = wallpostId;
    }

    @Basic
    @Column(name = "message", nullable = true, length = 512)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "timestamp", nullable = true)
    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Basic
    @Column(name = "picture", nullable = true)
    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WallPostEntity that = (WallPostEntity) o;

        if (wallpostId != that.wallpostId) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (!Arrays.equals(picture, that.picture)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wallpostId;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(picture);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "wall", referencedColumnName = "wall_id", nullable = false)
    public WallEntity getWallByWall() {
        return wallByWall;
    }

    public void setWallByWall(WallEntity wallByWall) {
        this.wallByWall = wallByWall;
    }

    @ManyToOne
    @JoinColumn(name = "author", referencedColumnName = "user_id")
    public UserEntity getUserByAuthor() {
        return userByAuthor;
    }

    public void setUserByAuthor(UserEntity userByAuthor) {
        this.userByAuthor = userByAuthor;
    }
}
