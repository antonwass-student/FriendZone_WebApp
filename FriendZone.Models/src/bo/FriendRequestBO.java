package bo;

/**
 * Created by Anton on 2016-11-17.
 */
public class FriendRequestBO {

    private UserSmallBO sender;
    private UserSmallBO receiver;
    private String message;

    public FriendRequestBO (String arg){}
    public FriendRequestBO (){}

    public UserSmallBO getSender() {
        return sender;
    }

    public void setSender(UserSmallBO sender) {
        this.sender = sender;
    }

    public UserSmallBO getReceiver() {
        return receiver;
    }

    public void setReceiver(UserSmallBO receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
