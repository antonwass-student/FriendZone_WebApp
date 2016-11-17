package main.java.rest;

import bo.LoginBO;
import bo.LoginResponseBO;
import bo.RegisterBO;
import bo.UserSmallBO;
import main.java.entities.UserEntity;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
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

    @Path("/get/session")
    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public UserSmallBO getUserBySession(String id){
        EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();

        TypedQuery<UserEntity> query = em.createQuery("FROM UserEntity WHERE session_id = :sid",
                UserEntity.class);

        query.setParameter("sid", id);

        try{
            UserEntity userEntity = query.getSingleResult();
            UserSmallBO usr = new UserSmallBO();
            usr.setId(userEntity.getUserId());
            usr.setMail(userEntity.getEmail());
            usr.setName(userEntity.getName());
            return usr;

        }catch(Exception e){
            System.out.println("Could not find user with that session id.");
            return null;
        }

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

        LoginResponseBO responseBO = new LoginResponseBO();

        System.out.println("User logging in");

        EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();
        System.out.println("Entered login operation.");

        TypedQuery<UserEntity> query = em.createQuery(
                "FROM UserEntity WHERE password = :pass AND email = :mail",
                UserEntity.class);

        query.setParameter("mail", credentials.getEmail())
                .setParameter("pass", credentials.getPassword());

        try{

            UserEntity user = query.getSingleResult();
            user.setSession_id(credentials.getSession_id());
            em.getTransaction().begin();
            responseBO.setLoggedIn(true);
            em.getTransaction().commit();

        }catch(Exception e){
            System.out.println("Could not find user.");
            responseBO.setLoggedIn(false);
        }

        return responseBO;
    }

    @Path("/register")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String register(RegisterBO userInfo){
        EntityManager em = Persistence.createEntityManagerFactory("persistenceUnit").createEntityManager();
        System.out.println("Received registration data.");


        em.getTransaction().begin();

        UserEntity u = new UserEntity();
        u.setName(userInfo.getName());
        u.setEmail(userInfo.getEmail());
        u.setPassword(userInfo.getPassword());
        u.setSession_id("");

        em.persist(u);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.flush();
        em.getTransaction().commit();
        new WallService().addWall(String.valueOf(u.getUserId()));

        System.out.println(userInfo.getEmail() + ":" + userInfo.getName());

        return "Registered";
    }
}


