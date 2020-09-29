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

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna os dados de uma conversa com um id específico")
    public ResponseEntity<Conversation> getConversationById(@PathVariable("id") long id) {
        return ResponseEntity.ok(
            conversationRepository.findConversationById(id)
        );
    }

    @PostMapping
    @ApiOperation(value = "Insere os dados de uma conversa")
    public ResponseEntity<Conversation> insert(@RequestBody Conversation conversation) {
        return ResponseEntity.ok(
            conversationRepository.save(conversation)
        );
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza os dados de uma conversa")
    public ResponseEntity<Conversation> update(@PathVariable("id") long id,
                                               @RequestBody Conversation newConversation) {
        Conversation conversation = conversationRepository.findConversationById(id);

        if(newConversation.getTitle() != null) conversation.setTitle(newConversation.getTitle());
    
        return ResponseEntity.ok(
            conversationRepository.save(conversation)
        );
    }

    @PostMapping("/{id}/members/add/{memberId}")
    @ApiOperation(value = "Adiciona um membro a uma conversa")
    public ResponseEntity<Conversation> insertConversationMember(@PathVariable("id") long id,
                                        @PathVariable("memberId") long memberId) {
        Conversation conversation = conversationRepository.findConversationById(id);
        Member member = memberRepository.findMemberById(memberId);

        conversation.addMember(member);

        return ResponseEntity.ok(
            conversationRepository.save(conversation)
        );
    }

    @DeleteMapping("/{id}/members/add/{memberId}")
    @ApiOperation(value = "Deleta um membro de uma conversa")
    public void deleteConversationMember(@PathVariable("id") long id,
                                         @PathVariable("memberId") long memberId) {
        Conversation conversation = conversationRepository.findConversationById(id);
        Member member = memberRepository.findMemberById(memberId);

        conversation.removeMember(member);

        conversationRepository.save(conversation);
    }

    @PostMapping("/{id}/messages/add/{messageId}")
    @ApiOperation(value = "Adiciona uma mensagem a uma conversa")
    public ResponseEntity<Conversation> insertConversationMessage(@PathVariable("id") long id,
                                        @PathVariable("messageId") long messageId) {
        Conversation conversation = conversationRepository.findConversationById(id);
        Message message = messageRepository.findMessageById(messageId);

        conversation.addMessage(message);

        return ResponseEntity.ok(
            conversationRepository.save(conversation)
        );
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta os dados de uma conversa")
    public void delete(@PathVariable("id") long id) {
        conversationRepository.deleteById(id);
    }
}