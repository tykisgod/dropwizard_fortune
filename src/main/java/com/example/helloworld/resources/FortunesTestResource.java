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
        /*
         * HTTP GET
         * invoke by:
         * curl -XGET localhost:8080/test/frequency
         *
         * Simulate sending 'GET /fortune (com.example.helloworld.resources.FortuneResource)' {times} times.
         * Default Simulation times is 100.
         * 1. Show frequency of each fortune occurs;
         * 2. Show all visible fortunes in fortune container object;
         * 3. Show all fortunes in fortune container object.
         * */
        fortuneContainer = ObjectIO.getFortuneContainer();
        int testTimes = times.orElse(100);
        String testOutput = fortuneContainer.frequencyTest(testTimes);
        LOGGER.info("test result " + testOutput);
        ObjectIO.WriteObjectToFile(fortuneContainer);
        return testOutput;
    }

    @Path("/list")
    @GET
    public String showFortuneList(@QueryParam("type") Optional<Integer> type) {
        /*
         * HTTP GET
         * invoke by:
         * curl -XGET localhost:8080/test/list
         *
         * Show all/visible fortunes in fortune container object.
         * */
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
