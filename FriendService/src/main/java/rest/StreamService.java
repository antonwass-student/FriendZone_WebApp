package main.java.rest;

import bo.StreamGetRequestBO;
import bo.StreamGetResponseBO;
import bo.UserSmallBO;
import bo.WallPostBO;
import main.java.entities.FriendshipEntity;
import main.java.entities.UserEntity;
import main.java.entities.WallEntity;
import main.java.entities.WallPostEntity;
import org.hibernate.Criteria;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.*;

/**
 * Created by Anton on 2016-11-18.
 */
@Path("/stream")
public class StreamService {
    @Path("/load")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public StreamGetResponseBO loadStream(StreamGetRequestBO streamRequest){
        EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();

        TypedQuery<UserEntity> queryUser = em.createQuery("FROM UserEntity WHERE session_id = :sid", UserEntity.class);
        queryUser.setParameter("sid", streamRequest.getUserSessionId());
        UserEntity user = queryUser.getSingleResult();

        //Get all friends

        Collection<FriendshipEntity> friends = user.getFriendshipsByUserId();
        friends.addAll(user.getFriendshipsByUserId_0());

        //Build criteria to get posts where author is friend with user.

        CriteriaBuilder cb = em.getCriteriaBuilder();
        //
        CriteriaQuery<WallPostEntity> cq = cb.createQuery(WallPostEntity.class);
        Root<WallPostEntity> root = cq.from(WallPostEntity.class);
        List<Predicate> predicates = new ArrayList<Predicate>();

        Expression<UserEntity> exp = root.get("userByAuthor");

        Predicate predicate1 = exp.in(friends);

        // try to get posts that are posted on a friends wall even if the author is not a friend.

        Expression<UserEntity> exp2 = root.get("wallByWall").get("userByOwner");

        Predicate predicate2 = exp2.in(friends);
        //----

        predicates.add(predicate1);
        predicates.add(predicate2);

        cq.where(cb.or(predicates.toArray(new Predicate[]{})));
        cq.orderBy(cb.desc(root.get("wallpostId")));
        //create query with criteria

        TypedQuery<WallPostEntity> postQuery = em.createQuery(cq);

        //execute query

        Collection<WallPostEntity> posts = postQuery.getResultList();

        StreamGetResponseBO response = new StreamGetResponseBO();

        ArrayList<WallPostBO> postBOs = new ArrayList<>();

        for(WallPostEntity p : posts){
            WallPostBO wp = new WallPostBO();
            wp.setId(p.getWallpostId());
            wp.setPicture(p.getPicture());
            wp.setMessage(p.getMessage());
            wp.setTimestamp(p.getTimestamp());

            UserSmallBO author = new UserSmallBO();
            author.setId(p.getUserByAuthor().getUserId());
            author.setName(p.getUserByAuthor().getName());
            author.setMail(p.getUserByAuthor().getEmail());
            wp.setAuthor(author);

            UserSmallBO ownerOfWall = new UserSmallBO();
            UserEntity wallOwner = p.getWallByWall().getUserByOwner();
            ownerOfWall.setId(wallOwner.getUserId());
            ownerOfWall.setName(wallOwner.getName());
            ownerOfWall.setMail(wallOwner.getEmail());
            wp.setOwnerOfWall(ownerOfWall);

            postBOs.add(wp);
        }


        /*
        // Vi borde göra orderby i frågorna till db ist. klart!
        Collections.sort(postBOs, new Comparator<WallPostBO>(){
            @Override
            public int compare(WallPostBO p1, WallPostBO p2){
                return p2.getTimestamp().compareTo(p1.getTimestamp());
            }
        });*/

        response.setPosts(postBOs);

        return response;
    }
}
