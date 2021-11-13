package com.joshuacodes.rabbit.client;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 *
 */
@Path("/rabbit")
@Singleton
public class RabbitController {

    @Inject
    QueueConnection queueConnection;


    @Path("/send")
    @POST
    public String sendMessage(String message) throws IOException {
        queueConnection.sendMessage(message);
        return "Sending Message";
    }
}
