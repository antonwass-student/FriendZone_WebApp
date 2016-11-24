package entities;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Anton on 2016-11-23.
 */
@Entity
@Table(name = "Message", schema = "dbo", catalog = "community")
public class MessageEntity {
    private int messageId;
    private Date sent;
    private String text;
    private UsrEntity usrBySender;
    private ConversationEntity conversationByReceiver;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id", nullable = false)
    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "sent", nullable = true)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    public Date getSent() {
        return sent;
    }

    public void setSent(Date sent) {
        this.sent = sent;
    }

    @Basic
    @Column(name = "text", nullable = true, length = 255)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageEntity that = (MessageEntity) o;

        if (messageId != that.messageId) return false;
        if (sent != null ? !sent.equals(that.sent) : that.sent != null) return false;
        if (text != null ? !text.equals(that.text) : that.text != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageId;
        result = 31 * result + (sent != null ? sent.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "sender", referencedColumnName = "user_id", nullable = false)
    public UsrEntity getUsrBySender() {
        return usrBySender;
    }

    public void setUsrBySender(UsrEntity usrBySender) {
        this.usrBySender = usrBySender;
    }

    @ManyToOne
    @JoinColumn(name = "receiver", referencedColumnName = "conversation_id", nullable = false)
    public ConversationEntity getConversationByReceiver() {
        return conversationByReceiver;
    }

    public void setConversationByReceiver(ConversationEntity conversationByReceiver) {
        this.conversationByReceiver = conversationByReceiver;
    }
}
