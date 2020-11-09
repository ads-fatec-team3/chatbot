package br.gov.sp.fatec.backend.services;

import java.util.List;

import br.gov.sp.fatec.backend.models.Agenda;

public interface AgendaService {
  public List<Agenda> getAllAgendas();

  public Agenda getAgendaById(long agendaId);

  public Agenda getAgendaByMember(long memberId);

  public Agenda createAgenda(Agenda agenda, long ownerId);

  public Agenda addMemberToAgenda(long memberId, long agendaId);

  public Agenda updateAgendaById(long agendaId, Agenda data);

  public void deleteAgendaById(long agendaId);
}