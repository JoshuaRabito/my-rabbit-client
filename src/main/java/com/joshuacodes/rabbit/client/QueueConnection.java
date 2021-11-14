package com.joshuacodes.rabbit.client;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class QueueConnection {

	private static final String QUEUE_NAME = "idk";
	private static final String EXCHANGE_NAME = "myExchange";
	private ConnectionFactory connectionFactory;

	public QueueConnection()
			throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
		this.connectionFactory = createConnectionFactory();

	}

	public void sendMessage(String message) throws IOException {

		try (Connection connection = connectionFactory.newConnection(); Channel channel = connection.createChannel()) {
			channel.queueDeclare(QUEUE_NAME, false, false, false, null);
			channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
			System.out.println(" [x] Sent '" + message + "'");

		} catch (TimeoutException e) {
			e.printStackTrace();
		}

	}

	public String getMessage() throws IOException {
		try (Connection connection = connectionFactory.newConnection();
				Channel channel = connection.createChannel()) {
			GetResponse response = channel.basicGet(QUEUE_NAME, true);
			String message = "";
			if (response == null) {
				// No message retrieved.
			} else {
				AMQP.BasicProperties props = response.getProps();
				byte[] body = response.getBody();
				long deliveryTag = response.getEnvelope().getDeliveryTag();
				message = new String(body, "UTF-8");
				System.out.println(" [x] pulled message: '" + message + "'");

			}
			return message;
		} catch (TimeoutException e) {
			throw new IllegalStateException(e);
		}

	}

	private ConnectionFactory createConnectionFactory() {
		ConnectionFactory connection = new ConnectionFactory();
		connection.setUsername("guest");
		connection.setPassword("guest");
		connection.setVirtualHost("/");
		connection.setHost("localhost");
		connection.setPort(5672);
		return connection;
	}
}
