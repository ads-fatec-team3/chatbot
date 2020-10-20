package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.exceptions.ConversationException.ConversationCrudException;
import br.gov.sp.fatec.backend.exceptions.ConversationException.ConversationNotFoundException;
import br.gov.sp.fatec.backend.exceptions.MemberException.MemberNotFoundException;
import br.gov.sp.fatec.backend.exceptions.MessageException.MessageNotFoundException;

import br.gov.sp.fatec.backend.models.Conversation;
import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.models.Message;
import br.gov.sp.fatec.backend.repositories.ConversationRepository;
import br.gov.sp.fatec.backend.repositories.MemberRepository;
import br.gov.sp.fatec.backend.repositories.MessageRepository;
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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/conversations")
@Api("API REST Gruly Conversation")
@CrossOrigin(origins = "*")
public class ConversationController {
  @Autowired
  private ConversationRepository conversationRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private MessageRepository messageRepository;

  @JsonView(Views.DetailConversationView.class)
  @GetMapping
  @ApiOperation(value = "Retorna uma lista com os dados de todas as conversas")
  public ResponseEntity<List<Conversation>> getAllConversations() {
    List<Conversation> conversations = conversationRepository.findAll();

    return ResponseEntity.ok(conversations);
  }

  @JsonView(Views.DetailConversationView.class)
  @GetMapping("/{conversationId}")
  @ApiOperation(value = "Retorna os dados de uma conversa")
  public ResponseEntity<Conversation> getConversationById(@PathVariable("conversationId") long conversationId) throws ConversationNotFoundException {
    Conversation fetchedChat = conversationRepository.findConversationById(conversationId);

    if(fetchedChat == null) {
      throw new ConversationNotFoundException(conversationId);
    }
    
    return ResponseEntity.ok(fetchedChat);
  }
  
  @PostMapping
  @ApiOperation(value = "Insere os dados de uma conversa")
  public ResponseEntity<Conversation> insert(@RequestBody Conversation conversation) throws ConversationCrudException {
    Conversation newChat = conversationRepository.save(conversation);
    
    if(newChat == null) {
      throw new ConversationCrudException("erro ao criar uma conversa");
    }

    return ResponseEntity.status(HttpStatus.CREATED).build();
  }

  @PutMapping("/{conversationId}")
  @ApiOperation(value = "Atualiza os dados de uma conversa")
  public ResponseEntity<Conversation> update(@PathVariable("conversationId") long conversationId,
                                             @RequestBody Conversation chatDataToUpdate) throws ConversationNotFoundException, ConversationCrudException {
    Conversation chat = conversationRepository.findConversationById(conversationId);

    if(chat == null) {
      throw new ConversationNotFoundException(conversationId);
    }

    if(chatDataToUpdate.getTitle() != null) chat.setTitle(chatDataToUpdate.getTitle());
    if(chatDataToUpdate.getMembers() != null) chat.setMembers(chatDataToUpdate.getMembers());
    if(chatDataToUpdate.getMessages() != null) chat.setMessages(chatDataToUpdate.getMessages());

    Conversation updatedChat = conversationRepository.save(chat);

    if(updatedChat == null) {
      throw new ConversationCrudException(String.format("erro ao atualizar os dados da conversa de id = %d", conversationId));
    }

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{conversationId}")
  @ApiOperation(value = "Deleta os dados de uma conversa")
  public ResponseEntity<Conversation> delete(@PathVariable("conversationId") long conversationId) throws ConversationNotFoundException, ConversationCrudException {
    Conversation chatToDelete = conversationRepository.findConversationById(conversationId);

    if(chatToDelete == null) {
      throw new ConversationNotFoundException(conversationId);
    }

    conversationRepository.deleteById(conversationId);

    return ResponseEntity.ok().build();
  }

  @PutMapping("/{conversationId}/members/{memberId}/add")
  @ApiOperation(value = "Adiciona um membro a uma conversa")
  public ResponseEntity<Conversation> insertConversationMember(@PathVariable("conversationId") long conversationId,
                                                               @PathVariable("memberId") long memberId) throws MemberNotFoundException,
                                                                                                               ConversationNotFoundException,
                                                                                                               ConversationCrudException {
    Conversation chat = conversationRepository.findConversationById(conversationId);
    
    if(chat == null) {
      throw new ConversationNotFoundException(conversationId);
    }
    
    if(chat.getMembers().stream().filter(member -> member.getId() == memberId).count() > 0) {
      throw new ConversationCrudException("membro já adicionado na conversa");
    }
    
    Member memberToAdd = memberRepository.findMemberById(memberId);

    if(memberToAdd == null) {
      throw new MemberNotFoundException(memberId);
    }

    chat.addMember(memberToAdd);
    conversationRepository.save(chat);
    
    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{conversationId}/members/{memberId}/remove")
  @ApiOperation(value = "Remove um membro de uma conversa")
  public ResponseEntity<Conversation> deleteConversationMember(@PathVariable("conversationId") long conversationId,
                                                               @PathVariable("memberId") long memberId) throws MemberNotFoundException, ConversationNotFoundException {
    Conversation chat = conversationRepository.findConversationById(conversationId);
    
    if(chat == null) {
      throw new ConversationNotFoundException(conversationId);
    }

    if(chat.getMembers().stream().filter(member -> member.getId() == memberId).count() == 0) {
      throw new MemberNotFoundException(memberId);
    }
    
    Member memberToRemove = memberRepository.findMemberById(memberId);

    if(memberToRemove == null) {
      throw new MemberNotFoundException(memberId);
    }

    chat.removeMember(memberToRemove);
    conversationRepository.save(chat);

    return ResponseEntity.ok().build();
  }

  @PutMapping("/{conversationId}/messages/{messageId}/add")
  @ApiOperation(value = "Adiciona uma mensagem a uma conversa")
  public ResponseEntity<Conversation> insertConversationMessage(@PathVariable("conversationId") long conversationId,
                                                                @PathVariable("messageId") long messageId) throws MessageNotFoundException,
                                                                                                                  ConversationNotFoundException,
                                                                                                                  ConversationCrudException {
    Conversation chat = conversationRepository.findConversationById(conversationId);
    
    if(chat == null) {
      throw new ConversationNotFoundException(conversationId);
    }

    if(chat.getMessages().stream().filter(message -> message.getId() == messageId).count() > 0) {
      throw new ConversationCrudException("mensagem já adicionada na conversa");
    }
    
    Message messageToAdd = messageRepository.findMessageById(messageId);
    
    if(messageToAdd == null) {
      throw new MessageNotFoundException(messageId);
    }

    chat.addMessage(messageToAdd);
    conversationRepository.save(chat);

    return ResponseEntity.ok().build();
  }
}