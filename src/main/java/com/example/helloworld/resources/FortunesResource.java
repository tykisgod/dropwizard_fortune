package com.example.helloworld.resources;

import com.example.helloworld.ObjectIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/fortunes")
@Produces(MediaType.APPLICATION_JSON)
public class FortunesResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(FortunesResource.class);

    private FortuneContainer fortuneContainer;

    public FortunesResource() {

    }


    @POST
    public int addFortune(@QueryParam("fortune") Optional<String> fortune) {
        /*
         * HTTP POST
         * invoke by:
         * curl -XPOST localhost:8080/fortunes?fortune={content of fortune}
         *
         * for example:
         * curl -XPOST localhost:8080/fortunes?fortune="Hello World!"
         * */
        fortuneContainer = ObjectIO.getFortuneContainer();
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

    @Path("{fortuneId}")
    @DELETE
    public String deleteFortune(@PathParam("fortuneId") Optional<Integer> id) {
        /*
         * HTTP POST
         * invoke by:
         * curl -XDELETE localhost:8080/fortunes/{fortuneId}
         *
         * for example:
         * curl -XDELETE localhost:8080/fortunes/1
         * */
        fortuneContainer = ObjectIO.getFortuneContainer();
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

}
