package bo;

/**
 * Created by Anton on 2016-11-21.
 */
public class FriendshipDeleteBO {
    private String user_session_id;
    private int friendshipId;

    public FriendshipDeleteBO(String arg) {
    }
    public FriendshipDeleteBO() {
    }

    public String getUser_session_id() {
        return user_session_id;
    }

    public void setUser_session_id(String user_session_id) {
        this.user_session_id = user_session_id;
    }

    public int getFriendshipId() {
        return friendshipId;
    }

    public void setFriendshipId(int friendshipId) {
        this.friendshipId = friendshipId;
    }
}
