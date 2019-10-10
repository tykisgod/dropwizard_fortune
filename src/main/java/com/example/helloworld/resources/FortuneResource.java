package com.example.helloworld.resources;

import com.example.helloworld.ObjectIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


@Path("/fortune") // This is where the HTTP URL path is set
@Produces(MediaType.APPLICATION_JSON)
public class FortuneResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldResource.class);

    private FortuneContainer fortuneContainer;

    public FortuneResource() {
        
    }


    @GET
    public String getFortune() {
        fortuneContainer = ObjectIO.getFortuneContainer();
        String output = fortuneContainer.get();
        LOGGER.info("get fortune: " + output);
        return output;
    }


}
