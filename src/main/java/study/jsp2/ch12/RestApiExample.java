package study.jsp2.ch12;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/test")
public class RestApiExample {

    public RestApiExample() {
        System.out.println("RestApiExample loaded!");
    }
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String sayHello() {
        return "Hello API Service";
    }

    @POST
    public String sayHello(@QueryParam("msg") String msg) {
        return msg + " API Service";
    }
}