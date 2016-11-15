package entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Anton on 2016-11-10.
 */
@Entity
@Table(name = "Friendship", schema = "dbo", catalog = "community")
public class FriendshipEntity {
    private Integer friendshipId;
    private Timestamp started;

    @Id
    @Column(name = "friendship_id", nullable = false)
    public Integer getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(Integer friendshipId) {
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

        if (friendshipId != null ? !friendshipId.equals(that.friendshipId) : that.friendshipId != null) return false;
        if (started != null ? !started.equals(that.started) : that.started != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = friendshipId != null ? friendshipId.hashCode() : 0;
        result = 31 * result + (started != null ? started.hashCode() : 0);
        return result;
    }
}
