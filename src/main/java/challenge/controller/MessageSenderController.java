package challenge.controller;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import challenge.exception.InvalidMessageException;
import challenge.model.Message;
import challenge.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
public class MessageSenderController {

	@Autowired
	private MessageService messageService;

	@PostMapping("wa/v1/send/")
	@Operation(summary = "Record and sends a message", description = "Inserts a message in database after validating it. Then it sends the mssage to a queue in Rabbit MQ")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully sent"),
			@ApiResponse(responseCode = "404", description = "Endpoint not found"),
			@ApiResponse(responseCode = "400", description = "Missing or invalid request body"),
			@ApiResponse(responseCode = "500", description = "Internal error") })

	public ResponseEntity<?> send2Phone(Model model, @RequestBody Message message)
			throws IOException, TimeoutException {
		try {
			Message result = messageService.send(message);
			return new ResponseEntity<Message>(result, HttpStatus.OK);
		} catch (InvalidMessageException e) {
			return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}
	}
}
