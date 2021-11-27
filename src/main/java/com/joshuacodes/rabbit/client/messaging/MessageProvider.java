package com.joshuacodes.rabbit.client.messaging;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Produces;

class MessageProvider {
	
	@Inject
	QueueConnection queueConnection;
	
	@Produces
	@Singleton
	MessageReceiver createMessageReceiver() {
		return new MessageReceiver(queueConnection);
	}
	
	@Produces
	@Singleton
	MessageSender createMessageSender() {
		return new MessageSender(queueConnection);
	}
	

}
