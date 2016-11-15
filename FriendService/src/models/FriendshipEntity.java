package models;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Anton on 2016-11-15.
 */
@Entity
@Table(name = "Friendship", schema = "dbo", catalog = "community")
public class FriendshipEntity {
    private Integer friendshipId;
    private Integer inviter;
    private Integer receiver;
    private Timestamp started;
    private UserEntity userByInviter;
    private UserEntity userByReceiver;

    @Id
    @Column(name = "friendship_id", nullable = false)
    public Integer getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(Integer friendshipId) {
        this.friendshipId = friendshipId;
    }

    @Basic
    @Column(name = "inviter", nullable = false)
    public Integer getInviter() {
        return inviter;
    }

    public void setInviter(Integer inviter) {
        this.inviter = inviter;
    }

    @Basic
    @Column(name = "receiver", nullable = false)
    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
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

        if (friendshipId != null ? !friendshipId.equals(that.friendshipId) : that.friendshipId != null) return false;
        if (inviter != null ? !inviter.equals(that.inviter) : that.inviter != null) return false;
        if (receiver != null ? !receiver.equals(that.receiver) : that.receiver != null) return false;
        if (started != null ? !started.equals(that.started) : that.started != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = friendshipId != null ? friendshipId.hashCode() : 0;
        result = 31 * result + (inviter != null ? inviter.hashCode() : 0);
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
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
