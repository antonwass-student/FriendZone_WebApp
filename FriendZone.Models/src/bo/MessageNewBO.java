package bo;

/**
 * Created by Anton on 2016-11-23.
 */
public class MessageNewBO {

    private String text;
    private int target_conversation_ID;
    private String sender_session_ID; //to verify that the sender actually is in the conversation


    public MessageNewBO(){ }

    public MessageNewBO(String arg){}

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTarget_conversation_ID() {
        return target_conversation_ID;
    }

    public void setTarget_conversation_ID(int target_conversation_ID) {
        this.target_conversation_ID = target_conversation_ID;
    }

    public String getSender_session_ID() {
        return sender_session_ID;
    }

    public void setSender_session_ID(String sender_session_ID) {
        this.sender_session_ID = sender_session_ID;
    }
}
