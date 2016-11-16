package main.java.rest;

import main.java.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Produces;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
/**
 * Created by Anton on 2016-11-15.
 */
@Path("/hello")
public class UserService {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserEntity getUser(){

        EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();

        UserEntity user = em.find(UserEntity.class, 1);

        return user;
    }
}


