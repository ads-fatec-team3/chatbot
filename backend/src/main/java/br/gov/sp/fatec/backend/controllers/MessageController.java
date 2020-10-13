package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.models.Conversation;
import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.models.Message;
import br.gov.sp.fatec.backend.repositories.ConversationRepository;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import br.gov.sp.fatec.backend.repositories.MessageRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
  private ConversationRepository conversationRepository;

  @Autowired
  private MemberRepository memberRepository;

  @GetMapping
  @ApiOperation(value = "Retorna uma lista com os dados de todas as mensagens")
  public List<Message> getAllMessages() {
    return messageRepository.findAll();
  }

  @GetMapping("/{messageId}")
  @ApiOperation(value = "Retorna os dados de uma mensagem")
  public ResponseEntity<Message> getMessageById(@PathVariable("messageId") long messageId) {
    return ResponseEntity.ok(messageRepository.findMessageById(messageId));
  }

  @PostMapping
  @ApiOperation(value = "Insere os dados de uma mensagem")
  public ResponseEntity<Message> insert(@RequestBody Message message, @RequestParam long senderId, @RequestParam long conversationId) {
    Member sender = memberRepository.findMemberById(senderId);
    Conversation conversation = conversationRepository.findConversationById(conversationId);
    
    message.setSender(sender);
    message.setConversation(conversation);

    return ResponseEntity.ok(messageRepository.save(message));
  }

  @PutMapping("/{messageId}")
  @ApiOperation(value = "Atualiza os dados de uma mensagem")
  public ResponseEntity<Message> update(@PathVariable("messageId") long messageId,
                                        @RequestBody Message updatedMessage) {
    Member sender = memberRepository.findMemberById(updatedMessage.getSender().getId());
    Conversation conversation = conversationRepository.findConversationById(updatedMessage.getConversation().getId());
    Message message = messageRepository.findMessageById(messageId);

    message.setText(updatedMessage.getText());
    message.setTimestamp(updatedMessage.getTimestamp());
    message.setSender(sender);
    message.setConversation(conversation);

    return ResponseEntity.ok(messageRepository.save(message));
  }

  @DeleteMapping("/{messageId}")
  @ApiOperation(value = "Deleta os dados de uma mensagem")
  public void delete(@PathVariable("messageId") long messageId) {
    messageRepository.deleteById(messageId);
  }
}