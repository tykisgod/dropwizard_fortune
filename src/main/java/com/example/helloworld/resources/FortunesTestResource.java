package com.example.helloworld.resources;

import com.example.helloworld.ObjectIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.Optional;


@Path("/test")
@Produces(MediaType.APPLICATION_JSON)
public class FortunesTestResource {
    private static final Logger LOGGER = LoggerFactory.getLogger(FortunesTestResource.class);

    private FortuneContainer fortuneContainer;

    public FortunesTestResource() {

    }

    @Path("/frequency")
    @GET
    public String testFortuneContainer(@QueryParam("times") Optional<Integer> times) {
        fortuneContainer = ObjectIO.getFortuneContainer();
        int testTimes = times.orElse(100);
        String testOutput = fortuneContainer.frequencyTest(testTimes);
        LOGGER.info("test result " + testOutput);
        return testOutput;
    }

    @Path("/list")
    @GET
    public String showFortuneList(@QueryParam("type") Optional<Integer> type) {
        fortuneContainer = ObjectIO.getFortuneContainer();
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
