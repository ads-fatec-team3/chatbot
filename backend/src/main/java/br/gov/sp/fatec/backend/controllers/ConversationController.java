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

  @GetMapping
  @ApiOperation(value = "Retorna uma lista com os dados de todas as conversas")
  public List<Conversation> getAllConversations() {
    return conversationRepository.findAll();
  }

  @GetMapping("/{conversationId}")
  @ApiOperation(value = "Retorna os dados de uma conversa")
  public ResponseEntity<GrulyApiExceptionResponse<Conversation>> getConversationById(@PathVariable("conversationId") long conversationId) {
    GrulyApiExceptionResponse<Conversation> response = new GrulyApiExceptionResponse<Conversation>();

    Conversation fetchedChat = conversationRepository.findConversationById(conversationId);
    
    if(fetchedChat == null) {
      response.addErrorMessage("conversa não encontrada");

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    response.setData(fetchedChat);

    return ResponseEntity.ok(response);
  }

  @PostMapping
  @ApiOperation(value = "Insere os dados de uma conversa")
  public ResponseEntity<GrulyApiExceptionResponse<Conversation>> insert(@RequestBody Conversation conversation) {
    GrulyApiExceptionResponse<Conversation> response = new GrulyApiExceptionResponse<Conversation>();

    Conversation newChat = conversationRepository.save(conversation);
    
    if(newChat == null) {
      response.addErrorMessage("erro ao criar uma conversa");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    response.setData(newChat);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/{conversationId}")
  @ApiOperation(value = "Atualiza os dados de uma conversa")
  public ResponseEntity<GrulyApiExceptionResponse<Conversation>> update(@PathVariable("conversationId") long conversationId,
                                             @RequestBody Conversation chatDataToUpdate) {
    GrulyApiExceptionResponse<Conversation> response = new GrulyApiExceptionResponse<Conversation>();

    Conversation chat = conversationRepository.findConversationById(conversationId);
    if(chat == null) {
      response.addErrorMessage("conversa não encontrada");
      
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    if(chatDataToUpdate.getTitle() != null) chat.setTitle(chatDataToUpdate.getTitle());
    if(chatDataToUpdate.getMembers() != null) chat.setMembers(chatDataToUpdate.getMembers());
    if(chatDataToUpdate.getMessages() != null) chat.setMessages(chatDataToUpdate.getMessages());

    Conversation updatedChat = conversationRepository.save(chat);

    if(updatedChat == null) {
      response.addErrorMessage("erro ao atualizar os dados da conversa");

      return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    response.setData(updatedChat);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{conversationId}")
  @ApiOperation(value = "Deleta os dados de uma conversa")
  public ResponseEntity<GrulyApiExceptionResponse<Conversation>> delete(@PathVariable("conversationId") long conversationId) {
    GrulyApiExceptionResponse<Conversation> response = new GrulyApiExceptionResponse<Conversation>();

    Conversation chatToDelete = conversationRepository.findConversationById(conversationId);

    if(chatToDelete == null) {
      response.addErrorMessage("conversa não encontrada");

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    conversationRepository.deleteById(conversationId);

    return ResponseEntity.ok().build();
  }

  @PutMapping("/{conversationId}/members/{memberId}/add")
  @ApiOperation(value = "Adiciona um membro a uma conversa")
  public ResponseEntity<GrulyApiExceptionResponse<Conversation>> insertConversationMember(@PathVariable("conversationId") long conversationId,
                                                               @PathVariable("memberId") long memberId) {
    GrulyApiExceptionResponse<Conversation> response = new GrulyApiExceptionResponse<Conversation>();

    Conversation chat = conversationRepository.findConversationById(conversationId);
    Member memberToAdd = memberRepository.findMemberById(memberId);

    if(chat == null || memberToAdd == null) {
      if(chat == null) response.addErrorMessage("conversa não encontrada");
      if(memberToAdd == null) response.addErrorMessage("membro não encontrado");
      
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    chat.addMember(memberToAdd);
    conversationRepository.save(chat);

    response.setData(chat);

    return ResponseEntity.ok(response);
  }

  @DeleteMapping("/{conversationId}/members/{memberId}/delete")
  @ApiOperation(value = "Deleta um membro de uma conversa")
  public ResponseEntity<GrulyApiExceptionResponse<Conversation>> deleteConversationMember(@PathVariable("conversationId") long conversationId,
                                                                                          @PathVariable("memberId") long memberId) {
    GrulyApiExceptionResponse<Conversation> response = new GrulyApiExceptionResponse<Conversation>();

    Conversation chat = conversationRepository.findConversationById(conversationId);
    Member memberToDelete = memberRepository.findMemberById(memberId);

    if(chat == null || memberToDelete == null) {
      if(chat == null) response.addErrorMessage("conversa não encontrada");
      if(memberToDelete == null) response.addErrorMessage("membro não encontrado");

      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    chat.removeMember(memberToDelete);
    conversationRepository.save(chat);

    response.setData(chat);

    return ResponseEntity.ok(response);
  }

  @PutMapping("/{conversationId}/messages/{messageId}/add")
  @ApiOperation(value = "Adiciona uma mensagem a uma conversa")
  public ResponseEntity<Conversation> insertConversationMessage(@PathVariable("conversationId") long conversationId, @RequestBody long messageId) {
    Conversation conversation = conversationRepository.findConversationById(conversationId);
    Message message = messageRepository.findMessageById(messageId);

    conversation.addMessage(message);

    return ResponseEntity.ok(conversationRepository.save(conversation));
  }
}