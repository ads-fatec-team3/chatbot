package br.gov.sp.fatec.backend.exceptions;

public class AgendaException {
  public static class AgendaNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AgendaNotFoundException(long agendaId) {
      super(String.format("agenda com o id = %d não encontrada", agendaId));
    }
  }

  public static class AgendaWithMemberNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AgendaWithMemberNotFoundException(long agendaId) {
      super(String.format("agenda com o membro = %d não encontrada", agendaId));
    }
  }

  public static class AgendaCrudException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public AgendaCrudException(String message) {
      super(message);
    }
  }
}