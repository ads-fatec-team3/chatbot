package br.gov.sp.fatec.backend.views;

public class Views {
  public interface SummaryMemberView {}
  public interface DetailMemberView extends SummaryMemberView {}
  
  public interface SummaryConversationView {}
  public interface DetailConversationView extends SummaryConversationView {}

  public interface SummaryMessageView {}
  public interface DetailMessageView extends SummaryMessageView {}

  public interface SummaryMemberRoleView {}
  public interface DetailMemberRoleView extends SummaryMemberRoleView {}

  public interface SummaryRolePrivilegeView {}
  public interface DetailRolePrivilegeView extends SummaryRolePrivilegeView {}

  public interface SummaryAgendaView {}
  public interface DetailAgendaView extends SummaryAgendaView {}

  public interface SummaryAuthView {}
}