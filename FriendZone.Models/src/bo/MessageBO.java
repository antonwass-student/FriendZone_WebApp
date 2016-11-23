package bo;

import java.sql.Timestamp;

/**
 * Created by Anton on 2016-11-23.
 */
public class MessageBO {
    private UserSmallBO sender;
    private String text;
    private Timestamp sent;

    public MessageBO(){}
    public MessageBO(String arg){}

    public UserSmallBO getSender() {
        return sender;
    }

    public void setSender(UserSmallBO sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Timestamp getSent() {
        return sent;
    }

    public void setSent(Timestamp sent) {
        this.sent = sent;
    }
}
