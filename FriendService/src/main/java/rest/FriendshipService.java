package main.java.rest;

import bo.FriendRequestDecisionBO;
import bo.FriendRequestBO;
import bo.FriendRequestNewBO;
import bo.UserSmallBO;
import main.java.entities.FriendRequestEntity;
import main.java.entities.FriendshipEntity;
import main.java.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Anton on 2016-11-17.
 */
@Path("/friend")
public class FriendshipService {

    @Path("/request/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<FriendRequestBO> getAllIncommingRequestsForUser(String user_session_id){
        try{
            EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();

            TypedQuery<UserEntity> query = em.createQuery("FROM UserEntity WHERE session_id = :sid", UserEntity.class);

            query.setParameter("sid", user_session_id);

            UserEntity user = query.getSingleResult();


            //getFriendRequestsByUserId_0 = receiver
            Collection<FriendRequestEntity> requests = user.getFriendRequestsByUserId_0();

            Collection<FriendRequestBO> requestBOs = new ArrayList<>();

            for(FriendRequestEntity fe : requests){
                FriendRequestBO req = new FriendRequestBO();
                req.setMessage(fe.getMessage());

                //persistence objects
                UserEntity senderEntity = fe.getUserBySender();
                UserEntity receiverEntity = fe.getUserByReceiver();

                //business objects
                UserSmallBO sender = new UserSmallBO();
                UserSmallBO receiver = new UserSmallBO();

                sender.setMail(senderEntity.getEmail());
                sender.setId(senderEntity.getUserId());
                sender.setName(senderEntity.getName());

                sender.setMail(receiverEntity.getEmail());
                sender.setName(receiverEntity.getName());
                sender.setId(receiverEntity.getUserId());

                req.setReceiver(receiver);
                req.setSender(sender);

                requestBOs.add(req);
            }

            System.out.println("Successfully retrieved friend requests for user.");
            return requestBOs;

        }catch(Exception e){
            System.out.println("Could not retrieve friend requests for user.");
            return null;
        }
    }

    @Path("/request/send")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String sendFriendRequest(FriendRequestNewBO invitation){
        try{

            EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();

            FriendRequestEntity request = new FriendRequestEntity();

            TypedQuery<UserEntity> query = em.createQuery("FROM UserEntity WHERE session_id = :sid", UserEntity.class);
            query.setParameter("sid", invitation.getSender_sid());
            UserEntity sender = query.getSingleResult();
            UserEntity receiver = em.find(UserEntity.class, invitation.getReceiver_user_id());

            request.setMessage(invitation.getMessage());
            request.setUserByReceiver(receiver);
            request.setUserBySender(sender);

            em.getTransaction().begin();
            em.persist(request);
            em.getTransaction().commit();

            System.out.println("Successfully sent friendrequest.");

        return "success";

        }catch(Exception e){
            e.printStackTrace();
            return "Server error. Could not fulfill request.";
        }
    }


    @Path("/request/decision")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public String acceptFriendRequest(FriendRequestDecisionBO decision){
        EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();

        try{
            if(decision.isDecision() == false){
                //remove the friendrequest.

                em.getTransaction().begin();

                FriendRequestEntity fre = em.find(FriendRequestEntity.class, decision.getRequest_id());

                em.remove(fre);

                em.getTransaction().commit();

                return "Friendrequest declined and removed.";

            }else{
                //create friendship
                em.getTransaction().begin();
                FriendshipEntity friendshipEntity = new FriendshipEntity();

                FriendRequestEntity friendRequest = em.find(FriendRequestEntity.class, decision.getRequest_id());
                UserEntity receiver = em.find(UserEntity.class, friendRequest.getUserByReceiver());
                UserEntity sender = em.find(UserEntity.class, friendRequest.getUserBySender());

                friendshipEntity.setUserByReceiver(receiver);
                friendshipEntity.setUserByInviter(sender);

                em.persist(friendshipEntity); //create friendship

                em.remove(friendRequest);     //remove request

                em.getTransaction().commit();
                return "New friendship created!";
            }
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Could not handle friendship request. Server error.");
            return null;
        }
    }

}
