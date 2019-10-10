package com.example.helloworld.resources;

import com.example.helloworld.ObjectIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/hello-world") // This is where the HTTP URL path is set
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldResource.class);

    private FortuneContainer fortuneContainer;

    public HelloWorldResource(FortuneContainer fortuneContainer) {
        this.fortuneContainer = fortuneContainer;
    }


    @GET
    public String getFortune() {
        String output = fortuneContainer.get();
        LOGGER.info("get fortune: " + output);
        return output;
    }


    @POST
    public int addFortune(@QueryParam("fortune") Optional<String> fortune) {
        if (fortune.isPresent()) {
            int newId = fortuneContainer.add(fortune.get());
            LOGGER.info(String.format("received a fortune: %s with id %s", fortune, newId));
            ObjectIO.WriteObjectToFile(fortuneContainer);
            return newId;
        } else {
            LOGGER.info("fortune content not supplied");
            return -1;
        }
    }

    @DELETE
    public String deleteFortune(@QueryParam("id") Optional<Integer> id) {
        if (id.isPresent()) {
            LOGGER.info(String.format("Try to delete fortune with id:= %s and with content: %s", id.get(), fortuneContainer.getFortuneById(id.get())));
            boolean hasDeleted = fortuneContainer.delete(id.get());
            if (hasDeleted) {
                ObjectIO.WriteObjectToFile(fortuneContainer);
                return "delete successfully!";
            } else {
                LOGGER.info(String.format("No fortune has id: " + id.get()));
            }
        } else {
            LOGGER.info("delete. id not supplied");
        }
        return "no such id!";
    }

    @Path("/test")
    @GET
    public String testFortuneContainer(@QueryParam("times") Optional<Integer> times) {
        int testTimes = times.orElse(100);
        String testOutput = fortuneContainer.frequencyTest(testTimes);
        LOGGER.info("test result " + testOutput);
        return testOutput;
    }

    @Path("/show")
    @GET
    public String showFortuneList(@QueryParam("type") Optional<Integer> type) {
        int showType = type.orElse(0);
        StringBuffer fortuneList = new StringBuffer();
        if (showType == 0) {
            fortuneList.append(fortuneContainer.getVisFortuneList());
        } else {
            fortuneList.append(fortuneContainer.toString());
        }
        LOGGER.info("fortuneList " + fortuneList);
        return fortuneList.toString();
    }

}
