package br.gov.sp.fatec.backend.controllers;

import br.gov.sp.fatec.backend.models.Agenda;
import br.gov.sp.fatec.backend.services.AgendaService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/agenda")
@Api("API REST Gruly Agenda")
@CrossOrigin(origins = "*")
public class AgendaController {
  @Autowired
  private AgendaService agendaService;

  @GetMapping
  @ApiOperation(value = "Retorna uma lista com os dados de todas as atividades")
  @JsonView(Views.SummaryConversationView.class)
  public ResponseEntity<List<Agenda>> getAllAgendas() {
    return ResponseEntity.ok(agendaService.getAllAgendas());
  }

  @JsonView(Views.DetailAgendaView.class)
  @GetMapping("/{agendaId}")
  @ApiOperation(value = "Retorna os dados de uma atividade")
  public ResponseEntity<Agenda> getAgendaById(@PathVariable("agendaId") long agendaId) {
    return ResponseEntity.ok(agendaService.getAgendaById(agendaId));
  }

  @JsonView(Views.SummaryConversationView.class)
  @GetMapping("/member/{memberId}")
  @ApiOperation(value = "Retorna os dados de uma atividade com um determinado membro")
  public ResponseEntity<Agenda> getAgendaByMember(@PathVariable("memberId") long memberId) {
    return ResponseEntity.ok(agendaService.getAgendaByMember(memberId));
  }

  @JsonView(Views.SummaryAgendaView.class)
  @PostMapping
  @ApiOperation(value = "Insere os dados de uma atividade")
  public ResponseEntity<Agenda> createAgenda(@RequestBody Agenda agenda, @RequestParam("ownerId") long ownerId) {
    return ResponseEntity.ok(agendaService.createAgenda(agenda, ownerId));
  }

  @PutMapping("/{agendaId}/members/{memberId}/add")
  @ApiOperation(value = "Adiciona um membro a uma atividade")
  public ResponseEntity<Agenda> addMemberToAgenda(@PathVariable("agendaId") long agendaId,
      @PathVariable("memberId") long memberId) {
    agendaService.addMemberToAgenda(memberId, agendaId);

    return ResponseEntity.ok().build();
  }

  @PutMapping("/{agendaId}")
  @ApiOperation(value = "Atualiza os dados de uma atividade")
  public ResponseEntity<Agenda> updateAgendaById(@PathVariable("agendaId") long agendaId, @RequestBody Agenda data) {
    agendaService.updateAgendaById(agendaId, data);

    return ResponseEntity.ok().build();
  }

  @DeleteMapping("/{agendaId}")
  @ApiOperation(value = "Deleta os dados de uma atividade")
  public ResponseEntity<Agenda> deleteAgendaById(@PathVariable("agendaId") long agendaId) {
    agendaService.deleteAgendaById(agendaId);

    return ResponseEntity.ok().build();
  }
}