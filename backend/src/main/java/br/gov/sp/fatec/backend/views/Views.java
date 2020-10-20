package br.gov.sp.fatec.backend.views;

public class Views {
  public interface SummaryMemberView {}
  public interface DetailMemberView extends SummaryMemberView {}
  
  public interface SummaryConversationView {}
  public interface DetailConversationView extends SummaryConversationView {}

  public interface SummaryMessageView {}
  public interface DetailMessageView extends SummaryMessageView {}
}