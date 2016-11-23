package rest;


import bo.ConversationNewBO;
import bo.MessageNewBO;
import entities.ConversationEntity;
import entities.MessageEntity;
import entities.UsrEntity;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;


/**
 * Created by Anton on 2016-11-23.
 */
@Path("/conversation")
public class ConversationService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage(){
        return "Hello world!";
    }


    @Path("/create")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String createNewConversation(ConversationNewBO newConvo){
        EntityManager em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        try{
            TypedQuery<UsrEntity> query = em.createQuery("FROM UsrEntity WHERE sessionId = :sid", UsrEntity.class);

            query.setParameter("sid", newConvo.getCreator_user_session_id());

            UsrEntity owner = query.getSingleResult();

            UsrEntity invited = em.find(UsrEntity.class, newConvo.getUser_to_invite());

            List<UsrEntity> usrs = Arrays.asList(owner, invited);

            ConversationEntity conversationEntity = new ConversationEntity();
            conversationEntity.setNamn(newConvo.getName());
            conversationEntity.setMembers(usrs);

            em.getTransaction().begin();
            em.persist(conversationEntity);
            em.getTransaction().commit();

            return "Conversation created.";

        }catch(Exception e){
            e.printStackTrace();
            return "Could not create conversation";
        }finally {
            em.close();
        }
    }

    @Path("/send")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    public String sendMessage(MessageNewBO newMessage){

        EntityManager em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();
        try{
            TypedQuery<UsrEntity> query = em.createQuery("FROM UsrEntity WHERE sessionId = :sid", UsrEntity.class);

            query.setParameter("sid", newMessage.getSender_session_ID());

            UsrEntity user = query.getSingleResult();

            ConversationEntity convo = em.find(ConversationEntity.class, newMessage.getTarget_conversation_ID());

            MessageEntity msg = new MessageEntity();

            msg.setText(newMessage.getText());
            msg.setConversationByReceiver(convo);
            msg.setUsrBySender(user);

            em.getTransaction().begin();
            em.persist(msg);
            em.getTransaction().commit();

            return "Message sent";
        }catch(Exception e){
            e.printStackTrace();
            return "Could not send message";
        }finally {
            em.close();
        }


    }


}
