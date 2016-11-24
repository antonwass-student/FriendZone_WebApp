package main.java.rest;

import bo.*;
import main.java.entities.FriendRequestEntity;
import main.java.entities.FriendshipEntity;
import main.java.entities.UserEntity;
import main.java.util.EntityManagerHelper;

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

    @Path("/friends/get/{session_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<UserSmallBO> getFriendsFrom(@PathParam("session_id")String id){
        try{
            EntityManager em = EntityManagerHelper.createEntityManager();
            TypedQuery<UserEntity> query = em.createQuery("FROM UserEntity WHERE session_id = :sid", UserEntity.class);
            query.setParameter("sid", id);
            UserEntity user = query.getSingleResult();
            Collection<UserSmallBO> friends = new ArrayList<>();
            Collection<FriendshipEntity> friendships = user.getFriendshipsByUserId();
            for(FriendshipEntity fe : friendships){
                UserSmallBO u = new UserSmallBO();
                if (user.getUserId() == fe.getUserByInviter().getUserId()){
                    u.setId(fe.getUserByReceiver().getUserId());
                    u.setName(fe.getUserByReceiver().getName());
                    u.setMail(fe.getUserByReceiver().getEmail());

                }else{
                    u.setId(fe.getUserByInviter().getUserId());
                    u.setName(fe.getUserByInviter().getName());
                    u.setMail(fe.getUserByInviter().getEmail());
                }

                friends.add(u);
            }
            return friends;
        }catch(Exception e){
            return null;
        }
    }

    @Path("/request/get/{session_id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<FriendRequestBO> getAllIncommingRequestsForUser(@PathParam("session_id") String user_session_id){
        try{
            EntityManager em = EntityManagerHelper.createEntityManager();

            TypedQuery<UserEntity> query = em.createQuery("FROM UserEntity WHERE session_id = :sid", UserEntity.class);

            query.setParameter("sid", user_session_id);

            UserEntity user = query.getSingleResult();


            //getFriendRequestsByUserId_0 = receiver
            Collection<FriendRequestEntity> requests = user.getFriendRequestsByUserId_0();

            Collection<FriendRequestBO> requestBOs = new ArrayList<>();

            for(FriendRequestEntity fe : requests){
                FriendRequestBO req = new FriendRequestBO();
                req.setMessage(fe.getMessage());
                req.setId(fe.getFriendRequestId());

                //persistence objects
                UserEntity senderEntity = fe.getUserBySender();
                UserEntity receiverEntity = fe.getUserByReceiver();

                //business objects
                UserSmallBO sender = new UserSmallBO();
                UserSmallBO receiver = new UserSmallBO();

                sender.setMail(senderEntity.getEmail());
                sender.setId(senderEntity.getUserId());
                sender.setName(senderEntity.getName());

                receiver.setMail(receiverEntity.getEmail());
                receiver.setName(receiverEntity.getName());
                receiver.setId(receiverEntity.getUserId());

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

            EntityManager em = EntityManagerHelper.createEntityManager();

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
    @Produces(MediaType.TEXT_PLAIN)
    public String acceptFriendRequest(FriendRequestDecisionBO decision){
        EntityManager em = EntityManagerHelper.createEntityManager();

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
                UserEntity receiver = friendRequest.getUserByReceiver();
                UserEntity sender = friendRequest.getUserBySender();

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

    @Path("/remove")
    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public String removeFriendship(FriendshipDeleteBO friendNoMore){
        EntityManager em = EntityManagerHelper.createEntityManager();
        try{
            TypedQuery<UserEntity> query = em.createQuery("FROM UserEntity  WHERE session_id = :sid", UserEntity.class);

            query.setParameter("sid", friendNoMore.getUser_session_id());

            UserEntity user = query.getSingleResult();

            FriendshipEntity friendship = em.find(FriendshipEntity.class, friendNoMore.getFriendshipId());

            if(friendship.getUserByInviter().getUserId() == user.getUserId() ||
                    friendship.getUserByReceiver().getUserId() == user.getUserId()){
                em.getTransaction().begin();
                em.remove(friendship);
                em.getTransaction().commit();
                return "Friendship removed.";
            }else{
                return "User is not part of friendship.";
            }



        }catch(Exception e){
            e.printStackTrace();
            return "An error occured, can not remove friendship.";
        }finally {
            em.close();
        }

    }

}
