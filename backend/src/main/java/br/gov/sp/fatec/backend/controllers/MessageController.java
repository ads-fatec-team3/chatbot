package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.models.Message;
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
@RequestMapping("/api/messages")
@Api("API REST Gruly Message")
@CrossOrigin(origins = "*")
public class MessageController {
    @Autowired
    private MessageRepository messageRepository;

    @GetMapping
    @ApiOperation(value = "Retorna uma lista com os dados de todas as mensagens")
    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "Retorna os dados de uma mensagem com um id específico")
    public ResponseEntity<Message> getMessageById(@PathVariable("id") long id) {
        return ResponseEntity.ok(
            messageRepository.findMessageById(id)
        );
    }

    @PostMapping
    @ApiOperation(value = "Insere os dados de uma mensagem")
    public ResponseEntity<Message> insert(@RequestBody Message message) {
        return ResponseEntity.ok(
            messageRepository.save(message)
        );
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "Atualiza os dados de uma mensagem")
    public ResponseEntity<Message> update(@PathVariable("id") long id,
                                          @RequestBody Message newMessage) {
        Message message = messageRepository.findMessageById(id);

        if(newMessage.getText() != null) message.setText(newMessage.getText());

        return ResponseEntity.ok(
            messageRepository.save(message)
        );
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "Deleta os dados de uma mensagem")
    public void delete(@PathVariable("id") long id) {
        messageRepository.deleteById(id);
    }
}