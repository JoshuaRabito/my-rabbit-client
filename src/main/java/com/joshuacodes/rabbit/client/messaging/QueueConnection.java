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
import javax.inject.Inject;

import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

@ApplicationScoped
class QueueConnection {

	public static final String QUEUE_NAME = "idk";
	public static final String EXCHANGE_NAME = "myExchange";
	private Connection connection;

	@Inject
    @ConfigProperty(name="rabbitHost")
	private String host;
	
	@Inject
    @ConfigProperty(name="rabbitUser")
	private String user;
	
	@Inject
    @ConfigProperty(name="rabbitPassword")
	private String pw;
	
	
	
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
		System.out.println("host is: "+ host);
		System.out.println("user is: "+ user);

		ConnectionFactory factory = new ConnectionFactory();
		factory.setUsername(user);
		factory.setPassword(pw);
		factory.setVirtualHost("/");
		factory.setHost(host);
		factory.setPort(5672);
		return factory;
	}

	public Connection getConnection() {
		return connection;
	}

}
