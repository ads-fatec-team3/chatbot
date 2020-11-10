package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.exceptions.MessageException.MessageNotFoundException;
import br.gov.sp.fatec.backend.models.Message;
import br.gov.sp.fatec.backend.services.MessageService;
import br.gov.sp.fatec.backend.views.Views;

import com.fasterxml.jackson.annotation.JsonView;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

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
  private MessageService messageService;

  @ApiOperation(value = "Retorna uma lista com os dados de todas as mensagens")
  @JsonView(Views.SummaryMessageView.class)
  @GetMapping
  public ResponseEntity<List<Message>> getAllMessages() {
    return ResponseEntity.ok(messageService.getAllMessages());
  }

  @ApiOperation(value = "Retorna os dados de uma mensagem")
  @JsonView(Views.DetailMessageView.class)
  @GetMapping("/{messageId}")
  public ResponseEntity<Message> getMessageById(@PathVariable("messageId") long messageId) {
    return ResponseEntity.ok(messageService.getMessageById(messageId));
  }

  @ApiOperation(value = "Insere os dados de uma mensagem")
  @PostMapping
  public ResponseEntity<Message> createMessage(@RequestBody Message message,
                                               @RequestParam("senderId") long senderId,
                                               @RequestParam("conversationId") long conversationId) {
    messageService.createMessage(message, senderId, conversationId);

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @ApiOperation(value = "Atualiza os dados de uma mensagem")
  @PutMapping("/{messageId}")
  public ResponseEntity<Message> updateMessageById(@PathVariable("messageId") long messageId,
                                                   @RequestBody Message messageDataToUpdate) {
    messageService.updateMessageById(messageId, messageDataToUpdate);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{messageId}")
  @ApiOperation(value = "Deleta os dados de uma mensagem")
  public ResponseEntity<Message> deleteMessageById(@PathVariable("messageId") long messageId) throws MessageNotFoundException {
    messageService.deleteMessageById(messageId);

    return ResponseEntity.ok().build();
  }
}