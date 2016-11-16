package main.java.entities;

import javax.persistence.*;

/**
 * Created by Anton on 2016-11-15.
 */
@Entity
@Table(name = "FriendRequest", schema = "dbo", catalog = "community")
public class FriendRequestEntity {
    private int friendRequestId;
    private String message;
    private UserEntity userBySender;
    private UserEntity userByReceiver;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendRequest_id", nullable = false)
    public int getFriendRequestId() {
        return friendRequestId;
    }

    public void setFriendRequestId(int friendRequestId) {
        this.friendRequestId = friendRequestId;
    }

    @Basic
    @Column(name = "message", nullable = true, length = 256)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendRequestEntity that = (FriendRequestEntity) o;

        if (friendRequestId != that.friendRequestId) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = friendRequestId;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "sender", referencedColumnName = "user_id", nullable = false)
    public UserEntity getUserBySender() {
        return userBySender;
    }

    public void setUserBySender(UserEntity userBySender) {
        this.userBySender = userBySender;
    }

    @ManyToOne
    @JoinColumn(name = "receiver", referencedColumnName = "user_id")
    public UserEntity getUserByReceiver() {
        return userByReceiver;
    }

    public void setUserByReceiver(UserEntity userByReceiver) {
        this.userByReceiver = userByReceiver;
    }
}
