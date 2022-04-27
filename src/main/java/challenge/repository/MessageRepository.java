package challenge.repository;

import org.springframework.data.repository.CrudRepository;

import challenge.model.Message;

public interface MessageRepository extends CrudRepository<Message, Integer> {

}
