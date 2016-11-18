package main.java.rest;

import bo.StreamGetRequestBO;
import bo.StreamGetResponseBO;
import bo.UserSmallBO;
import bo.WallPostBO;
import main.java.entities.FriendshipEntity;
import main.java.entities.UserEntity;
import main.java.entities.WallPostEntity;
import org.hibernate.Criteria;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import javax.ws.rs.*;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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

        Expression<UserEntity> exp = root.get("userByAuthor");

        Predicate predicate = exp.in(friends);

        cq.where(predicate);
        cq.orderBy(cb.asc(root.get("timestamp")));
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

            postBOs.add(wp);
        }

        response.setPosts(postBOs);

        return response;
    }
}
