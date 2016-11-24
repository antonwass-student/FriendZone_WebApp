package rest;


import bo.*;
import entities.ConversationEntity;
import entities.MessageEntity;
import entities.UsrEntity;
import util.EntityManagerHelper;

import javax.enterprise.context.Conversation;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
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

    @Path("/messages")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ConversationMessagesBO getMessages(ConversationRequestBO request){
        EntityManager em = EntityManagerHelper.createEntityManager();
        try{
            ConversationEntity conversation = em.find(ConversationEntity.class, request.getRequested_conversation_id());

            TypedQuery<UsrEntity> query = em.createQuery("FROM UsrEntity WHERE sessionId = :sid", UsrEntity.class);

            query.setParameter("sid", request.getUser_session_id());

            UsrEntity user = query.getSingleResult();

            if(conversation.getMessagesByConversationId().contains(user)){

                List<MessageBO> messages = new ArrayList();

                for(MessageEntity msg : conversation.getMessagesByConversationId()){
                    UsrEntity se = msg.getUsrBySender();
                    UserSmallBO sender = new UserSmallBO();
                    sender.setId(se.getUserId());
                    sender.setName(se.getName());
                    sender.setMail(se.getEmail());

                    MessageBO message = new MessageBO();
                    message.setText(msg.getText());
                    message.setSender(sender);
                    message.setSent(msg.getSent());

                    messages.add(message);
                }
                ConversationMessagesBO result = new ConversationMessagesBO();
                if (messages.isEmpty()) messages.add(new MessageBO());
                result.setMessages(messages);
                return result;
            }else{
                System.out.println("User is not eligible to get messages from this conversation.");
                return null;
            }

        }catch(Exception e){
            e.printStackTrace();
            System.out.println("error");
            return null;
        }finally {
            em.close();
        }
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
    @Produces(MediaType.APPLICATION_JSON)
    public MessageSentResponseBO sendMessage(MessageNewBO newMessage){

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

            MessageSentResponseBO response = new MessageSentResponseBO();
            List<String> sids = new ArrayList<>();
            response.setSession_ids_in_convo(sids);

            UserSmallBO sender = new UserSmallBO();
            sender.setId(user.getUserId());
            sender.setName(user.getName());
            sender.setMail(user.getEmail());

            response.setSender(sender);

            for(UsrEntity usr : convo.getMembers()){
                sids.add(usr.getSessionId());
            }

            return response;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally {
            em.close();
        }
    }

    @Path("/get/{userId}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ConversationListBO getConversationsByUser(@PathParam("userId")String userSessionId){
        EntityManager em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

        try{
            TypedQuery<UsrEntity> query = em.createQuery("FROM UsrEntity WHERE sessionId = :sid", UsrEntity.class);
            query.setParameter("sid", userSessionId);

            UsrEntity user = query.getSingleResult();

            ConversationListBO conversationList = new ConversationListBO();
            Collection<ConversationBO> conversations = new ArrayList();

            for(ConversationEntity ce : user.getConversations()){
                ConversationBO convo = new ConversationBO();

                Collection<UserSmallBO> members = new ArrayList<>();

                for(UsrEntity usr : ce.getMembers()){
                    UserSmallBO member = new UserSmallBO();
                    member.setId(usr.getUserId());
                    member.setMail(usr.getEmail());
                    member.setName(usr.getName());

                    members.add(member);
                }
                convo.setId(ce.getConversationId());
                convo.setMembers(members);
                convo.setTitle(ce.getNamn());

                conversations.add(convo);
            }

            conversationList.setConvos(conversations);

            return conversationList;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }finally {
            em.close();
        }

    }



}
