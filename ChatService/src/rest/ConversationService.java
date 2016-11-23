package rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

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
}
