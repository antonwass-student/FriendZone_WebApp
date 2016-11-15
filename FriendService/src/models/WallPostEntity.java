package models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Arrays;

/**
 * Created by Anton on 2016-11-15.
 */
@Entity
@Table(name = "WallPost", schema = "dbo", catalog = "community")
public class WallPostEntity {
    private Integer wallpostId;
    private Integer wall;
    private String message;
    private Timestamp timestamp;
    private byte[] picture;
    private Integer author;
    private WallEntity wallByWall;

    @Id
    @Column(name = "wallpost_id", nullable = false)
    public Integer getWallpostId() {
        return wallpostId;
    }

    public void setWallpostId(Integer wallpostId) {
        this.wallpostId = wallpostId;
    }

    @Basic
    @Column(name = "wall", nullable = false)
    public Integer getWall() {
        return wall;
    }

    public void setWall(Integer wall) {
        this.wall = wall;
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

    @Basic
    @Column(name = "author", nullable = true)
    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WallPostEntity that = (WallPostEntity) o;

        if (wallpostId != null ? !wallpostId.equals(that.wallpostId) : that.wallpostId != null) return false;
        if (wall != null ? !wall.equals(that.wall) : that.wall != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) return false;
        if (!Arrays.equals(picture, that.picture)) return false;
        if (author != null ? !author.equals(that.author) : that.author != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = wallpostId != null ? wallpostId.hashCode() : 0;
        result = 31 * result + (wall != null ? wall.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + Arrays.hashCode(picture);
        result = 31 * result + (author != null ? author.hashCode() : 0);
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
}
