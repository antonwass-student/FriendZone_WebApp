package models;

import javax.persistence.*;

/**
 * Created by Anton on 2016-11-15.
 */
@Entity
@Table(name = "FriendRequest", schema = "dbo", catalog = "community")
public class FriendRequestEntity {
    private Integer friendRequestId;
    private Integer sender;
    private String message;
    private Integer receiver;
    private UserEntity userBySender;
    private UserEntity userByReceiver;

    @Id
    @Column(name = "friendRequest_id", nullable = false)
    public Integer getFriendRequestId() {
        return friendRequestId;
    }

    public void setFriendRequestId(Integer friendRequestId) {
        this.friendRequestId = friendRequestId;
    }

    @Basic
    @Column(name = "sender", nullable = false)
    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    @Basic
    @Column(name = "message", nullable = true, length = 256)
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Basic
    @Column(name = "receiver", nullable = true)
    public Integer getReceiver() {
        return receiver;
    }

    public void setReceiver(Integer receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FriendRequestEntity that = (FriendRequestEntity) o;

        if (friendRequestId != null ? !friendRequestId.equals(that.friendRequestId) : that.friendRequestId != null)
            return false;
        if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;
        if (receiver != null ? !receiver.equals(that.receiver) : that.receiver != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = friendRequestId != null ? friendRequestId.hashCode() : 0;
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (receiver != null ? receiver.hashCode() : 0);
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
