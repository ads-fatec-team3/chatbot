package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.models.Conversation;
import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.models.Message;
import br.gov.sp.fatec.backend.repositories.ConversationRepository;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import br.gov.sp.fatec.backend.repositories.MessageRepository;
import br.gov.sp.fatec.backend.utils.GrulyApiExceptionResponse;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
  private ConversationRepository conversationRepository;

  @Autowired
  private MemberRepository memberRepository;

  @ExceptionHandler(MissingServletRequestParameterException.class)
  public ResponseEntity<GrulyApiExceptionResponse<Message>> handleMissingParams(MissingServletRequestParameterException ex) {
    GrulyApiExceptionResponse<Message> response = new GrulyApiExceptionResponse<Message>();
    response.addErrorMessage("falta o parâmetro " + ex.getParameterName());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
  }

  @GetMapping
  @ApiOperation(value = "Retorna uma lista com os dados de todas as mensagens")
  public List<Message> getAllMessages() {
    return messageRepository.findAll();
  }

  @GetMapping("/{messageId}")
  @ApiOperation(value = "Retorna os dados de uma mensagem")
  public ResponseEntity<GrulyApiExceptionResponse<Message>> getMessageById(@PathVariable("messageId") long messageId) {
    GrulyApiExceptionResponse<Message> response = new GrulyApiExceptionResponse<Message>();

    Message fetchedMessage = messageRepository.findMessageById(messageId);

    if(fetchedMessage == null) {
      response.addErrorMessage("mensagem não encontrada");
      
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    response.setData(fetchedMessage);

    return ResponseEntity.ok(response);
  }

  @PostMapping
  @ApiOperation(value = "Insere os dados de uma mensagem")
  public ResponseEntity<GrulyApiExceptionResponse<Message>> insert(@RequestBody Message message,
                                                                   @RequestParam("senderId") long senderId,
                                                                   @RequestParam("conversationId") long conversationId) {
    GrulyApiExceptionResponse<Message> response = new GrulyApiExceptionResponse<Message>();

    Member sender = memberRepository.findMemberById(senderId);
    Conversation chat = conversationRepository.findConversationById(conversationId);

    if(sender == null || chat == null) {
      if(sender == null) response.addErrorMessage("membro não encontrado");
      if(chat == null) response.addErrorMessage("conversa não encontrada");

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    message.setSender(sender);
    message.setConversation(chat);

    Message newMessage = messageRepository.save(message);

    if(newMessage == null) {
      response.addErrorMessage("erro ao criar mensagem");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{messageId}")
  @ApiOperation(value = "Atualiza os dados de uma mensagem")
  public ResponseEntity<GrulyApiExceptionResponse<Message>> update(@PathVariable("messageId") long messageId,
                                                                   @RequestBody Message messageDataToUpdate) {
    GrulyApiExceptionResponse<Message> response = new GrulyApiExceptionResponse<Message>();

    Message message = messageRepository.findMessageById(messageId);

    if(messageDataToUpdate.getText() != null) message.setText(messageDataToUpdate.getText());

    Message updatedMessage = messageRepository.save(message);

    if(updatedMessage == null) {
      response.addErrorMessage("erro ao atualizar os dados da mensagem");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{messageId}")
  @ApiOperation(value = "Deleta os dados de uma mensagem")
  public ResponseEntity<GrulyApiExceptionResponse<Message>> delete(@PathVariable("messageId") long messageId) {
    GrulyApiExceptionResponse<Message> response = new GrulyApiExceptionResponse<Message>();

    Message messageToDelete = messageRepository.findMessageById(messageId);

    if(messageToDelete == null) {
      response.addErrorMessage("mensagem não encontrada");

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    messageRepository.deleteById(messageId);

    return ResponseEntity.ok().build();
  }
}