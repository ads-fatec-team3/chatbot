package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.exceptions.MessageException.MessageCrudException;
import br.gov.sp.fatec.backend.exceptions.MessageException.MessageNotFoundException;

import br.gov.sp.fatec.backend.models.Message;
import br.gov.sp.fatec.backend.repositories.MessageRepository;
import br.gov.sp.fatec.backend.services.MessageService;
import br.gov.sp.fatec.backend.views.Views;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
@Api("API REST Gruly Message")
@CrossOrigin(origins = "*")
public class MessageController {
  @Autowired
  private MessageRepository messageRepository;
  
  @Autowired
  private MessageService messageService;

  @JsonView(Views.DetailMessageView.class)
  @GetMapping
  @ApiOperation(value = "Retorna uma lista com os dados de todas as mensagens")
  public ResponseEntity<List<Message>> getAllMessages() {
    List<Message> messages = messageRepository.findAll();

    return ResponseEntity.ok(messages);
  }

  @JsonView(Views.DetailMessageView.class)
  @GetMapping("/{messageId}")
  @ApiOperation(value = "Retorna os dados de uma mensagem")
  public ResponseEntity<Message> getMessageById(@PathVariable("messageId") long messageId) throws MessageNotFoundException {
    Message fetchedMessage = messageRepository.findMessageById(messageId);

    if(fetchedMessage == null) {
      throw new MessageNotFoundException(messageId);
    }

    return ResponseEntity.ok(fetchedMessage);
  }

  @PostMapping
  @ApiOperation(value = "Insere os dados de uma mensagem")
  public ResponseEntity<Message> insert(@RequestBody Message message,
                                        @RequestParam("senderId") long senderId,
                                        @RequestParam("conversationId") long conversationId) {
    messageService.createMessage(message, senderId, conversationId);
    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{messageId}")
  @ApiOperation(value = "Atualiza os dados de uma mensagem")
  public ResponseEntity<Message> update(@PathVariable("messageId") long messageId,
                                        @RequestBody Message messageDataToUpdate) throws MessageNotFoundException {
    Message message = messageRepository.findMessageById(messageId);

    if(message == null) {
      throw new MessageNotFoundException(messageId);
    }

    if(messageDataToUpdate.getText() != null) message.setText(messageDataToUpdate.getText());

    Message updatedMessage = messageRepository.save(message);

    if(updatedMessage == null) {
      throw new MessageCrudException(String.format("erro ao atualizar os dados da mensagem de id = %d", messageId));
    }

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{messageId}")
  @ApiOperation(value = "Deleta os dados de uma mensagem")
  public ResponseEntity<Message> delete(@PathVariable("messageId") long messageId) throws MessageNotFoundException {
    Message messageToDelete = messageRepository.findMessageById(messageId);

    if(messageToDelete == null) {
      throw new MessageNotFoundException(messageId);
    }

    messageRepository.deleteById(messageId);

    return ResponseEntity.ok().build();
  }
}