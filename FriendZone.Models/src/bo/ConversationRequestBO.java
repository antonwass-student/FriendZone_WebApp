package bo;

/**
 * Created by Anton on 2016-11-23.
 */
public class ConversationRequestBO {
    private String user_session_id;
    private int requested_conversation_id;

    public ConversationRequestBO(){}
    public ConversationRequestBO(String arg){}

    public String getUser_session_id() {
        return user_session_id;
    }

    public void setUser_session_id(String user_session_id) {
        this.user_session_id = user_session_id;
    }

    public int getRequested_conversation_id() {
        return requested_conversation_id;
    }

    public void setRequested_conversation_id(int requested_conversation_id) {
        this.requested_conversation_id = requested_conversation_id;
    }
}
