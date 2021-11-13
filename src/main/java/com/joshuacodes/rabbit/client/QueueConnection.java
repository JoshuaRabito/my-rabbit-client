package com.joshuacodes.rabbit.client;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
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
  private final Channel channel;
  private final Connection connection;

  public QueueConnection()
      throws IOException, TimeoutException, NoSuchAlgorithmException, KeyManagementException, URISyntaxException {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUsername("guest");
    factory.setPassword("guest");
    factory.setVirtualHost("/");
    factory.setHost("localhost");
    factory.setPort(5672);

    this.connection = factory.newConnection();
    this.channel = connection.createChannel();

    channel.queueDeclare(QUEUE_NAME, false, false, false, null);
  }

  @PreDestroy
  public void cleanup(){
    try {
      if(this.connection.isOpen()){
        this.connection.close();
      }

      if(this.channel.isOpen()){
        this.channel.close();
      }
    } catch (IOException | TimeoutException e) {
      e.printStackTrace();
    }
  }


  public void sendMessage(String message) throws IOException {
    channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
    System.out.println(" [x] Sent '" + message + "'");
  }
}
