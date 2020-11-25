package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.models.Conversation;
import br.gov.sp.fatec.backend.services.ConversationService;
import br.gov.sp.fatec.backend.views.Views;

import com.fasterxml.jackson.annotation.JsonView;

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
  private ConversationService conversationService;

  @ApiOperation(value = "Retorna uma lista com os dados de todas as conversas")
  @JsonView(Views.SummaryConversationView.class)
  @GetMapping
  public ResponseEntity<List<Conversation>> getAllConversations() {
    return ResponseEntity.ok(conversationService.getAllConversations());
  }

  @ApiOperation(value = "Retorna os dados de uma conversa")
  @JsonView(Views.DetailConversationView.class)
  @GetMapping("/{conversationId}")
  public ResponseEntity<Conversation> getConversationById(@PathVariable("conversationId") long conversationId) {
    return ResponseEntity.ok(conversationService.getConversationById(conversationId));
  }

  @JsonView(Views.DetailConversationView.class)
  @GetMapping("/members/{memberId}")
  public ResponseEntity<List<Conversation>> getConversationsByMemberId(@PathVariable("memberId") long memberId) {
    return ResponseEntity.ok(conversationService.getConversationsByMemberId(memberId));
  }
  
  @ApiOperation(value = "Insere os dados de uma conversa")
  @PostMapping
  public ResponseEntity<Conversation> createConversation(@RequestBody Conversation conversation) {
    Conversation newConversation = conversationService.createConversation(conversation);
    return ResponseEntity.ok(conversationService.getConversationById(newConversation.getId()));
  }

  @ApiOperation(value = "Atualiza os dados de uma conversa")
  @PutMapping("/{conversationId}")
  public ResponseEntity<Conversation> updateConversationById(@PathVariable("conversationId") long conversationId,
                                                             @RequestBody Conversation chatDataToUpdate) {
    conversationService.updateConversationById(conversationId, chatDataToUpdate);

    return ResponseEntity.ok().build();
  }

  @ApiOperation(value = "Deleta os dados de uma conversa")
  @DeleteMapping("/{conversationId}")
  public ResponseEntity<Conversation> deleteConversationById(@PathVariable("conversationId") long conversationId) {
    conversationService.deleteConversationById(conversationId);

    return ResponseEntity.ok().build();
  }

  @ApiOperation(value = "Adiciona um membro a uma conversa")
  @PutMapping("/{conversationId}/members/{memberId}/add")
  public ResponseEntity<Conversation> addMemberToConversation(@PathVariable("conversationId") long conversationId,
                                                              @PathVariable("memberId") long memberId) {
    conversationService.addMemberToConversation(memberId, conversationId);
    
    return ResponseEntity.ok().build();
  }

  @ApiOperation(value = "Remove um membro de uma conversa")
  @DeleteMapping("/{conversationId}/members/{memberId}/remove")
  public ResponseEntity<Conversation> removeMemberFromConversation(@PathVariable("conversationId") long conversationId,
                                                                   @PathVariable("memberId") long memberId) {
    conversationService.removeMemberFromConversation(memberId, conversationId);

    return ResponseEntity.ok().build();
  }
}