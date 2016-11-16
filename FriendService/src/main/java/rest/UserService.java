package main.java.rest;

import bo.LoginBO;
import bo.LoginResponseBO;
import bo.RegisterBO;
import main.java.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import org.hibernate.Session;
import org.hibernate.cfg.Configuration;

/**
 * Created by Anton on 2016-11-15.
 */
@Path("/user")
public class UserService {

    @Path("/get/one")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public UserEntity getUser(@QueryParam("id")int id){

        EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();

        UserEntity user = em.find(UserEntity.class, id);

        return user;
    }

    @Path("/get/all")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<UserEntity> getUsers(){
        EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();
        Query query = em.createQuery("SELECT u FROM UserEntity u");
        Collection<UserEntity> users = query.getResultList();

        //business object

        return users;
    }

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public LoginResponseBO login(LoginBO credentials){
        EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();
        System.out.println("Entered login operation.");
        /*
        Query query = em.createQuery("SELECT u FROM UserEntity WHERE u.email == ?");
        query.setParameter(0, credentials.getUsername());
        */

        System.out.println("User logging in: " + credentials.getEmail());

        LoginResponseBO responseBO = new LoginResponseBO();

        responseBO.setLoggedIn(true);


        return responseBO;
    }

    @Path("/register")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String register(RegisterBO userInfo){
        EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();
        System.out.println("Recieved registration data.");


        em.getTransaction().begin();

        UserEntity u = new UserEntity();
        u.setName(userInfo.getName());
        u.setEmail(userInfo.getEmail());
        u.setPassword(userInfo.getPassword());

        em.persist(u);
        em.getTransaction().commit();

        //Databasgrejsimojs
        System.out.println(userInfo.getEmail() + ":" + userInfo.getName());

        return "Registerd";
    }
}


