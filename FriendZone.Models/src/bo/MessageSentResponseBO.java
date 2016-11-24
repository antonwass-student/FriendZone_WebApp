package bo;

import java.util.Collection;

/**
 * Created by Anton on 2016-11-24.
 */
public class MessageSentResponseBO {
    private Collection<String> session_ids_in_convo;
    private UserSmallBO sender;

    public MessageSentResponseBO(String arg){}
    public MessageSentResponseBO(){}

    public Collection<String> getSession_ids_in_convo() {
        return session_ids_in_convo;
    }

    public void setSession_ids_in_convo(Collection<String> session_ids_in_convo) {
        this.session_ids_in_convo = session_ids_in_convo;
    }

    public UserSmallBO getSender() {
        return sender;
    }

    public void setSender(UserSmallBO sender) {
        this.sender = sender;
    }
}
