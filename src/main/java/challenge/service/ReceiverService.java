package challenge.service;

import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

@Component
public class ReceiverService {


	@Value("${messages.host}")
	private static String messagesHost;

	private final static String QUEUE_GROUP_NAME = "group1";

	Logger log = Logger.getLogger(ReceiverService.class.getName());

	@PostConstruct
	public void listening() throws Exception {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost(messagesHost);
		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		channel.queueDeclare(QUEUE_GROUP_NAME, false, false, false, null);
		log.info(" [*] Listening for messages");

		DeliverCallback deliverCallback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
			log.info(" [x] Received '" + message + "'");
		};
		channel.basicConsume(QUEUE_GROUP_NAME, true, deliverCallback, consumerTag -> {
		});
	}
}