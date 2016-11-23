package bo;

/**
 * Created by Anton on 2016-11-23.
 */
public class ConversationNewBO {
    private String name;
    private String creator_user_session_id;
    private int user_to_invite;

    public ConversationNewBO(String arg) {
    }

    public ConversationNewBO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreator_user_session_id() {
        return creator_user_session_id;
    }

    public void setCreator_user_session_id(String creator_user_session_id) {
        this.creator_user_session_id = creator_user_session_id;
    }

    public int getUser_to_invite() {
        return user_to_invite;
    }

    public void setUser_to_invite(int user_to_invite) {
        this.user_to_invite = user_to_invite;
    }
}
