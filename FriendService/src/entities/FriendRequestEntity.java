package entities;

import javax.persistence.*;

/**
 * Created by Anton on 2016-11-10.
 */
@Entity
@Table(name = "FriendRequest", schema = "dbo", catalog = "community")
public class FriendRequestEntity {
    private Integer friendRequestId;
    private String message;

    @Id
    @Column(name = "friendRequest_id", nullable = false)
    public Integer getFriendRequestId() {
        return friendRequestId;
    }

    public void setFriendRequestId(Integer friendRequestId) {
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

        if (friendRequestId != null ? !friendRequestId.equals(that.friendRequestId) : that.friendRequestId != null)
            return false;
        if (message != null ? !message.equals(that.message) : that.message != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = friendRequestId != null ? friendRequestId.hashCode() : 0;
        result = 31 * result + (message != null ? message.hashCode() : 0);
        return result;
    }
}
