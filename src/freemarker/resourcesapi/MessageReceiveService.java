package freemarker.resourcesapi;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;
 
@Path("/message")
public class MessageReceiveService {
 
    @GET
    public Response pingMe(){
     
        String defaultResp = "Hi... How are you?";
        return Response.status(200).entity(defaultResp).build();
    }
     
    @GET
    @Path("/birthday")
    public Response printBdayMessage(){
         
        String bDayMessage = "Happy Birthday";
        return Response.status(200).entity(bDayMessage).build();
    }
}
