package main.java.rest;

import bo.UserSmallBO;
import bo.WallBO;
import bo.WallPostBO;
import main.java.entities.UserEntity;
import main.java.entities.WallEntity;
import main.java.entities.WallPostEntity;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Anton on 2016-11-17.
 */
@Path("/wall")
public class WallService {

    @Path("/get")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public WallBO getWallByUser(String userId){

        EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();

        TypedQuery<UserEntity> query = em.createQuery("FROM UserEntity WHERE userId = :uid",
                UserEntity.class);

        try{

            UserEntity user = query.getSingleResult();
            UserSmallBO owner = new UserSmallBO();
            owner.setId(user.getUserId());
            owner.setName(user.getName());
            owner.setMail(user.getEmail());

            Collection<WallEntity> walls = user.getWallsByUserId();

            WallBO wall = new WallBO();

            WallEntity wallEntity = walls.iterator().next();

            Collection<WallPostBO> posts = new ArrayList<>();

            for(WallPostEntity wpe : wallEntity.getWallPostsByWallId()){
                WallPostBO post = new WallPostBO();
                post.setId(wpe.getWallpostId());
                post.setMessage(wpe.getMessage());
                post.setPicture(wpe.getPicture());
                post.setTimestamp(wpe.getTimestamp());

                UserSmallBO author = new UserSmallBO();
                UserEntity ae = wpe.getUserByAuthor();
                author.setId(ae.getUserId());
                author.setName(ae.getName());
                author.setName(ae.getName());

                post.setAuthor(author);

                posts.add(post);
            }

            wall.setId(wallEntity.getWallId());
            wall.setPosts(posts);
            wall.setOwner(owner);


            return wall;

        }catch(Exception e){
            System.out.println("Could not find user");
            return null;
        }
    }

    @Path("/post")
    @POST
    public String addWall(String userId){
        EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();

        TypedQuery<UserEntity> query = em.createQuery("FROM UserEntity WHERE userId = :uid",
                UserEntity.class);

        System.out.println("Adding wall for user " + userId);

        query.setParameter("uid", Integer.parseInt(userId));

        try{
            UserEntity user = query.getSingleResult();

            em.getTransaction().begin();
            WallEntity wallEntity = new WallEntity();
            wallEntity.setUserByOwner(user);
            em.persist(wallEntity);
            em.getTransaction().commit();

            return "Wall created.";
        }catch(Exception e){
            System.out.println("Could not find user.");
            return "Could not find user.";
        }
    }
}
