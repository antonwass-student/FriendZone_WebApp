package main.java.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Anton on 2016-11-15.
 */
@Entity
@Table(name = "Friendship", schema = "dbo", catalog = "community")
public class FriendshipEntity {
    private int friendshipId;
    private Timestamp started;
    private UserEntity userByInviter;
    private UserEntity userByReceiver;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendship_id", nullable = false)
    public int getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(int friendshipId) {
        this.friendshipId = friendshipId;
    }

    @Basic
    @Column(name = "started", nullable = true)
    public Timestamp getStarted() {
        return started;
    }

    public void setStarted(Timestamp started) {
        this.started = started;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendshipEntity that = (FriendshipEntity) o;

        if (friendshipId != that.friendshipId) return false;
        if (started != null ? !started.equals(that.started) : that.started != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = friendshipId;
        result = 31 * result + (started != null ? started.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "inviter", referencedColumnName = "user_id", nullable = false)
    public UserEntity getUserByInviter() {
        return userByInviter;
    }

    public void setUserByInviter(UserEntity userByInviter) {
        this.userByInviter = userByInviter;
    }

    @ManyToOne
    @JoinColumn(name = "receiver", referencedColumnName = "user_id", nullable = false)
    public UserEntity getUserByReceiver() {
        return userByReceiver;
    }

    public void setUserByReceiver(UserEntity userByReceiver) {
        this.userByReceiver = userByReceiver;
    }
}
