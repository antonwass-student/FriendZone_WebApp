package bo;

/**
 * Created by Anton on 2016-11-17.
 */
public class FriendRequestNewBO {

    private int id;
    private String sender_sid;
    private int receiver_user_id;
    private String message;

    public FriendRequestNewBO(String arg){}
    public FriendRequestNewBO(){}

    public int getReceiver_user_id() {
        return receiver_user_id;
    }

    public void setReceiver_user_id(int receiver_user_id) {
        this.receiver_user_id = receiver_user_id;
    }

    public String getSender_sid() {
        return sender_sid;
    }

    public void setSender_sid(String sender_sid) {
        this.sender_sid = sender_sid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
