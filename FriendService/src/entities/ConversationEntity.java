package entities;

import javax.persistence.*;
import java.util.Collection;

/**
 * Created by Anton on 2016-11-15.
 */
@Entity
@Table(name = "Conversation", schema = "dbo", catalog = "community")
public class ConversationEntity {
    private int conversationId;
    private String namn;
    private Collection<MessageEntity> messagesByConversationId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "conversation_id", nullable = false)
    public int getConversationId() {
        return conversationId;
    }

    public void setConversationId(int conversationId) {
        this.conversationId = conversationId;
    }

    @Basic
    @Column(name = "namn", nullable = false, length = 64)
    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConversationEntity that = (ConversationEntity) o;

        if (conversationId != that.conversationId) return false;
        if (namn != null ? !namn.equals(that.namn) : that.namn != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = conversationId;
        result = 31 * result + (namn != null ? namn.hashCode() : 0);
        return result;
    }

    @OneToMany(mappedBy = "conversationByReceiver")
    public Collection<MessageEntity> getMessagesByConversationId() {
        return messagesByConversationId;
    }

    public void setMessagesByConversationId(Collection<MessageEntity> messagesByConversationId) {
        this.messagesByConversationId = messagesByConversationId;
    }
}
