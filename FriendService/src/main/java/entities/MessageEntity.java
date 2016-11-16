package main.java.entities;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Anton on 2016-11-15.
 */
@Entity
@Table(name = "Message", schema = "dbo", catalog = "community")
public class MessageEntity {
    private int messageId;
    private int sender;
    private Timestamp sent;
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
    @Column(name = "sender", nullable = false)
    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
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

        if (messageId != that.messageId) return false;
        if (sender != that.sender) return false;
        if (sent != null ? !sent.equals(that.sent) : that.sent != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = messageId;
        result = 31 * result + sender;
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
