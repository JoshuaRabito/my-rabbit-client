package com.joshuacodes.rabbit.client.messaging;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.TimeoutException;

import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.event.Observes;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@ApplicationScoped
class QueueConnection {

	public static final String QUEUE_NAME = "idk";
	public static final String EXCHANGE_NAME = "myExchange";
	private Connection connection;

	public QueueConnection()
			throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {

	}

	public void init(@Observes @Initialized(ApplicationScoped.class) Object init) throws IOException, TimeoutException {
		System.out.println("initializing queue connection");
		ConnectionFactory connectionFactory = createConnectionFactory();
		this.connection = connectionFactory.newConnection();
	}

	
	public void destroy( @Observes @Destroyed( ApplicationScoped.class ) Object init ) {
		try {
			if (connection.isOpen()) {
				connection.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
	

	private ConnectionFactory createConnectionFactory() {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername("guest");
		factory.setPassword("guest");
		factory.setVirtualHost("/");
		factory.setHost("localhost");
		factory.setPort(5672);
		return factory;
	}

	public Connection getConnection() {
		return connection;
	}

}
