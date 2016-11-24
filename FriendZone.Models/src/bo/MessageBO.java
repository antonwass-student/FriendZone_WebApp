package bo;

import java.util.Date;

/**
 * Created by Anton on 2016-11-23.
 */
public class MessageBO {
    private UserSmallBO sender;
    private String text;
    private Date sent;

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

    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }
}
