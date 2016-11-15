package entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Anton on 2016-11-10.
 */
@Entity
@Table(name = "Message", schema = "dbo", catalog = "community")
public class MessageEntity {
    private Integer messageId;
    private Integer sender;
    private Timestamp sent;
    private ConversationEntity conversationByReceiver;

    @Id
    @Column(name = "message_id", nullable = false)
    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    @Basic
    @Column(name = "sender", nullable = false)
    public Integer getSender() {
        return sender;
    }

    public void setSender(Integer sender) {
        this.sender = sender;
    }

    @Basic
    @Column(name = "sent", nullable = false)
    public Timestamp getSent() {
        return sent;
    }

    public void setSent(Timestamp sent) {
        this.sent = sent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageEntity that = (MessageEntity) o;

        if (messageId != null ? !messageId.equals(that.messageId) : that.messageId != null) return false;
        if (sender != null ? !sender.equals(that.sender) : that.sender != null) return false;
        if (sent != null ? !sent.equals(that.sent) : that.sent != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageId != null ? messageId.hashCode() : 0;
        result = 31 * result + (sender != null ? sender.hashCode() : 0);
        result = 31 * result + (sent != null ? sent.hashCode() : 0);
        return result;
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
