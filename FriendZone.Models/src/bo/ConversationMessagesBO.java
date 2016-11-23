package bo;

import java.util.List;

/**
 * Created by Anton on 2016-11-23.
 */
public class ConversationMessagesBO {
    private List<MessageBO> messages;

    public ConversationMessagesBO(){}
    public ConversationMessagesBO(String arg){}

    public List<MessageBO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageBO> messages) {
        this.messages = messages;
    }
}
