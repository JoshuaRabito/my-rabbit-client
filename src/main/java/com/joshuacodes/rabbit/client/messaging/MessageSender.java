package com.joshuacodes.rabbit.client.messaging;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.rabbitmq.client.Channel;

public class MessageSender {
	
	
	
	private QueueConnection queueConnection;

	@Inject
	public MessageSender(QueueConnection connection) {
		super();
		this.queueConnection = connection;
	}

	public void sendMessage(String message) throws IOException {

		try ( Channel channel = queueConnection.getConnection().createChannel()) {
			channel.queueDeclare(queueConnection.QUEUE_NAME, false, false, false, null);
			channel.basicPublish("", queueConnection.QUEUE_NAME, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");

		} catch (TimeoutException e) {
			e.printStackTrace();
		}

	}

}
