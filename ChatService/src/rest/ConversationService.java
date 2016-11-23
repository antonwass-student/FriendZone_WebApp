package rest;


import entities.MessageEntity;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;


/**
 * Created by Anton on 2016-11-23.
 */
@Path("/chat")
public class ConversationService {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getMessage(){
        return "Hello world!";
    }


    @Path("/all")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getAllMessages(){
        EntityManager em = Persistence.createEntityManagerFactory("NewPersistenceUnit").createEntityManager();

        TypedQuery<MessageEntity> query = em.createQuery("FROM MessageEntity", MessageEntity.class);

        Collection<MessageEntity> messages = query.getResultList();

        String response = "";

        for(MessageEntity m : messages){
            response += m.getText() + ", ";
        }

        return response;
    }
}
