package main.java.rest;

import bo.FriendRequestBO;
import bo.UserSmallBO;
import main.java.entities.FriendRequestEntity;
import main.java.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.ws.rs.Consumes;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Anton on 2016-11-17.
 */
@Path("/friend")
public class FriendshipService {

    @Path("/request/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<FriendRequestBO> getAllIncommingRequestsForUser(String userId){
        try{
            EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();
            UserEntity user = em.find(UserEntity.class, Integer.parseInt(userId));

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
    @Consumes()
    public String sendFriendRequest(FriendRequestBO invitation){
        try{

            EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();

            FriendRequestEntity request = new FriendRequestEntity();

            UserEntity sender = em.find(UserEntity.class, invitation.getSender().getId());
            UserEntity receiver = em.find(UserEntity.class, invitation.getReceiver().getId());

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

}
