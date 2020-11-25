package br.gov.sp.fatec.backend.services;

import br.gov.sp.fatec.backend.exceptions.AgendaException.AgendaNotFoundException;
import br.gov.sp.fatec.backend.exceptions.AgendaException.AgendaCrudException;
import br.gov.sp.fatec.backend.exceptions.AgendaException.AgendaWithMemberNotFoundException;
import br.gov.sp.fatec.backend.exceptions.MemberException.MemberNotFoundException;
import br.gov.sp.fatec.backend.models.Agenda;
import br.gov.sp.fatec.backend.models.Member;
import br.gov.sp.fatec.backend.repositories.AgendaRepository;
import br.gov.sp.fatec.backend.repositories.MemberRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("AgendaService")
@Transactional
public class AgendaServiceImpl implements AgendaService {

  @Autowired
  private AgendaRepository agendaRepository;

  @Autowired
  private MemberRepository memberRepository;

  @Override
  @PreAuthorize("isAuthenticated()")
  public List<Agenda> getAllAgendas() {
    return agendaRepository.findAll();
  }

  @Override
  @PreAuthorize("isAuthenticated()")
  public Agenda getAgendaById(long agendaId) {
    Agenda fetchedAgenda = agendaRepository.findAgendById(agendaId);

    if (fetchedAgenda == null) {
      throw new AgendaNotFoundException(agendaId);
    }

    return fetchedAgenda;
  }

  @Override
  @PreAuthorize("isAuthenticated()")
  public Agenda getAgendaByMember(long memberId) {
    Agenda fetchedAgenda = agendaRepository.findByMember(memberId);

    if (fetchedAgenda == null) {
      throw new AgendaWithMemberNotFoundException(memberId);
    }

    return fetchedAgenda;
  }

  @Override
  @PreAuthorize("isAuthenticated()")
  public Agenda createAgenda(Agenda agenda, long ownerId) throws AgendaCrudException, MemberNotFoundException {
    Member owner = memberRepository.findMemberById(ownerId);

    if (owner == null) {
      throw new MemberNotFoundException(ownerId);
    }

    agenda.setOwner(owner);

    Agenda newAgenda = agendaRepository.save(agenda);

    if (newAgenda == null) {
      throw new AgendaCrudException("Erro ao criar uma atividade");
    }

    return newAgenda;
  }

  @Override
  @PreAuthorize("isAuthenticated()")
  public Agenda addMemberToAgenda(long memberId, long agendaId)
      throws MemberNotFoundException, AgendaNotFoundException, AgendaCrudException {
    Agenda agenda = agendaRepository.findAgendById(agendaId);

    if (agenda == null) {
      throw new AgendaNotFoundException(agendaId);
    }

    if (agenda.getMembers().stream().filter(member -> member.getId() == memberId).count() > 0) {
      throw new AgendaCrudException("Membro já adicionado na agenda");
    }

    Member member = memberRepository.findMemberById(memberId);

    if (member == null) {
      throw new MemberNotFoundException(memberId);
    }

    agenda.addMember(member);
    agendaRepository.save(agenda);

    return agenda;
  }

  @Override
  @PreAuthorize("isAuthenticated()")
  public Agenda updateAgendaById(long agendaId, Agenda data) throws AgendaNotFoundException {
    Agenda agenda = agendaRepository.findAgendById(agendaId);

    if (agenda == null) {
      throw new AgendaNotFoundException(agendaId);
    }

    if (data.getTitle() != null)
      agenda.setTitle(data.getTitle());
    if (data.getDescription() != null)
      agenda.setDescription(data.getDescription());
    if (data.getColor() != null)
      agenda.setColor(data.getColor());
    if (data.getDateBegin() != null)
      agenda.setDateBegin(data.getDateBegin());
    if (data.getDateEnd() != null)
      agenda.setDateEnd(data.getDateEnd());
    if (data.getStatus() != null)
      agenda.setStatus(data.getStatus());
    if (data.getMembers() != null)
      agenda.setMembers(data.getMembers());

    Agenda updatedAgenda = agendaRepository.save(agenda);

    if (updatedAgenda == null) {
      throw new AgendaCrudException(String.format("erro ao atualizar os dados da atividade de id = %d", agendaId));
    }

    return updatedAgenda;
  }

  @Override
  @PreAuthorize("isAuthenticated()")
  public void deleteAgendaById(long agendaId) throws AgendaNotFoundException {
    Agenda agenda = agendaRepository.findAgendById(agendaId);

    if (agenda == null) {
      throw new AgendaNotFoundException(agendaId);
    }

    agendaRepository.deleteById(agendaId);
  }
}