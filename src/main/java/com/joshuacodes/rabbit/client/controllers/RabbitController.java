package com.joshuacodes.rabbit.client.controllers;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.joshuacodes.rabbit.client.messaging.MessageReceiver;
import com.joshuacodes.rabbit.client.messaging.MessageSender;

/**
 *
 */
@Path("/rabbit")
@Singleton
public class RabbitController {

	private MessageSender messageSender;

	private MessageReceiver messageReceiver;

	public RabbitController() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Inject
	public RabbitController(MessageSender messageSender, MessageReceiver messageReceiver) {
		super();
		this.messageSender = messageSender;
		this.messageReceiver = messageReceiver;
	}

	@Path("/send")
	@POST 
	@Consumes({ MediaType.APPLICATION_JSON})
	public void sendMessage(String message) throws IOException {
		System.out.println("Message send is not null " + messageSender != null ? true : false);
		messageSender.sendMessage(message);
	}

	@Path("/receive")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String pullMessage() throws IOException {
		return messageReceiver.getMessage();
	}
}
