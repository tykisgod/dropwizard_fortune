package com.example.helloworld.resources;

import com.example.helloworld.ObjectIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello-world") // This is where the HTTP URL path is set
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldResource.class);

    private final AtomicLong counter;
    private FortuneContainer fortuneContainer;

    public HelloWorldResource(FortuneContainer fortuneContainer) {
        this.counter = new AtomicLong();
        this.fortuneContainer = fortuneContainer;
    }


    @GET
    public String sayHello() {
        String output = this.fortuneContainer.toString();
        fortuneContainer.addOne();
        ObjectIO.WriteObjectToFile(fortuneContainer);
        return output;
    }


    @POST
    public void receiveHello(@Valid Saying saying) {
        LOGGER.info("Received a saying: {}\n\n", saying);
    }

    @DELETE
    public void deleteIt(@QueryParam("id") Optional<String> id) {
        if (id.isPresent()) {
            LOGGER.info("delete object with id:=" + id.get());
            System.out.println("delete object with id:=" + id.get());
        } else {
            LOGGER.info("delete. id not supplied");
        }
    }
}
