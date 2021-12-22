package com.joshuacodes.rabbit.client.messaging;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;

public class MessageReceiver {

  QueueConnection queueConnection;

  @Inject
  public MessageReceiver(QueueConnection connection) {
    super();
    this.queueConnection = connection;
  }

  public String getMessage() throws IOException {
    try (Channel channel = queueConnection.getConnection().createChannel()) {
      GetResponse response = channel.basicGet(queueConnection.QUEUE_NAME, true);
      String message = "";
      if (response == null) {
        // No message retrieved.
      } else {
        AMQP.BasicProperties props = response.getProps();
        byte[] body = response.getBody();
        long deliveryTag = response.getEnvelope().getDeliveryTag();
        message = new String(body, "UTF-8");
        System.out.println("pulled message: '" + message + "' from the queue");

      }
      return message;
    } catch (TimeoutException e) {
      throw new IllegalStateException(e);
    }

  }

}
