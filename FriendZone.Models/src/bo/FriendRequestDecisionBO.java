package bo;

/**
 * Created by Anton on 2016-11-17.
 */
public class FriendRequestDecisionBO {
    private FriendRequestBO request;
    private boolean decision;
    private String receiver_session_id; //to verify that it is correct user accepting.

    public FriendRequestDecisionBO(String arg) {
    }

    public FriendRequestDecisionBO() {
    }

    public FriendRequestBO getRequest() {
        return request;
    }

    public void setRequest(FriendRequestBO request) {
        this.request = request;
    }

    public String getReceiver_session_id() {
        return receiver_session_id;
    }

    public void setReceiver_session_id(String receiver_session_id) {
        this.receiver_session_id = receiver_session_id;
    }

    public boolean isDecision() {
        return decision;
    }

    public void setDecision(boolean decision) {
        this.decision = decision;
    }
}
