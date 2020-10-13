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
  public ResponseEntity<Conversation> getConversationById(@PathVariable("conversationId") long conversationId) {
    return ResponseEntity.ok(conversationRepository.findConversationById(conversationId));
  }

  @PostMapping
  @ApiOperation(value = "Insere os dados de uma conversa")
  public ResponseEntity<Conversation> insert(@RequestBody Conversation conversation) {
    return ResponseEntity.ok(conversationRepository.save(conversation));
  }

  @PutMapping("/{conversationId}")
  @ApiOperation(value = "Atualiza os dados de uma conversa")
  public ResponseEntity<Conversation> update(@PathVariable("conversationId") long conversationId, @RequestBody Conversation updatedConversation) {
    Conversation conversation = conversationRepository.findConversationById(conversationId);
    if(updatedConversation.getTitle() != null) conversation.setTitle(updatedConversation.getTitle());
    if(updatedConversation.getMembers() != null) conversation.setMembers(updatedConversation.getMembers());
    if(updatedConversation.getMessages() != null) conversation.setMessages(updatedConversation.getMessages());

    return ResponseEntity.ok(conversationRepository.save(conversation));
  }

  @PostMapping("/{conversationId}/members/{memberId}")
  @ApiOperation(value = "Adiciona um membro a uma conversa")
  public ResponseEntity<Conversation> insertConversationMember(@PathVariable("conversationId") long conversationId, @PathVariable("memberId") long memberId) {
    Conversation conversation = conversationRepository.findConversationById(conversationId);
    Member member = memberRepository.findMemberById(memberId);

    conversation.addMember(member);
    
    return ResponseEntity.ok(conversationRepository.save(conversation));
  }

  @DeleteMapping("/{conversationId}/members/delete/{memberId}")
  @ApiOperation(value = "Deleta um membro de uma conversa")
  public void deleteConversationMember(@PathVariable("id") long conversationId, @PathVariable("memberId") long memberId) {
    Conversation conversation = conversationRepository.findConversationById(conversationId);
    Member member = memberRepository.findMemberById(memberId);

    conversation.removeMember(member);

    conversationRepository.save(conversation);
  }

  @PostMapping("/{conversationId}/messages/add/{messageId}")
  @ApiOperation(value = "Adiciona uma mensagem a uma conversa")
  public ResponseEntity<Conversation> insertConversationMessage(@PathVariable("conversationId") long conversationId, @RequestBody long messageId) {
    Conversation conversation = conversationRepository.findConversationById(conversationId);
    Message message = messageRepository.findMessageById(messageId);

    conversation.addMessage(message);

    return ResponseEntity.ok(conversationRepository.save(conversation));
  }

  @DeleteMapping("/{conversationId}")
  @ApiOperation(value = "Deleta os dados de uma conversa")
  public void delete(@PathVariable("conversationId") long conversationId) {
    conversationRepository.deleteById(conversationId);
  }
}