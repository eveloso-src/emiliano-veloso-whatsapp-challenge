package challenge.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import challenge.exception.InvalidMessageException;
import challenge.model.Message;
import challenge.repository.MessageRepository;

@SpringBootTest
public class MessageServiceTest {

	@Autowired
	MessageService messageService;

	@Mock
	private RabbitTemplate rabbitTemplate;
	@Mock
	private MessageRepository messageRepository;

	@Test
	public void testEmptyBody() {
		Message message = new Message();
		try {
			messageService.send(message);
			assertFalse(true);
		} catch (InvalidMessageException e) {
			assertTrue(e instanceof InvalidMessageException);
		}

	}

	@Test
	public void testFullMessageBody() {
		Message message = new Message();
		message.setDestination("123");
		message.setMessage("Content");
		message.setSource("ABC");
		Mockito.when(rabbitTemplate.convertSendAndReceive(Mockito.any(String.class), Mockito.any(String.class),
				Mockito.any(String.class))).thenReturn(null);
		Mockito.when(messageRepository.save(Mockito.any())).thenReturn(message);

		try {
			Message result = messageService.send(message);
			assertTrue(result.getDestination().equals("123"));
			assertTrue(result.getMessage().equals("Content"));
			assertTrue(result.getSource().equals("ABC"));
		} catch (InvalidMessageException e) {
			assertFalse(e instanceof InvalidMessageException);
		}

	}
}
