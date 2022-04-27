package challenge.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import challenge.exception.InvalidMessageException;
import challenge.model.Message;
import challenge.repository.MessageRepository;

@Service
public class MessageService {

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public Message save(Message message) {
		return messageRepository.save(message);
	}

	public Message send(Message message) throws InvalidMessageException {
		if (message == null || hasEmptyFields(message)) {
			throw new InvalidMessageException();
		}
		rabbitTemplate.convertAndSend("", message.getDestination(), message.getMessage());
		return save(message);

	}

	private boolean hasEmptyFields(Message message) {
		return ObjectUtils.isEmpty(message.getDestination()) || ObjectUtils.isEmpty(message.getDestination())
				|| ObjectUtils.isEmpty(message.getSource());
	}
}
