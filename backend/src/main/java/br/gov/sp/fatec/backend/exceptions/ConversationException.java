package br.gov.sp.fatec.backend.exceptions;

public class ConversationException {
  public static class ConversationNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ConversationNotFoundException(long conversationId) {
      super(String.format("conversa de id = %d não encontrada", conversationId));
    }
  }

  public static class ConversationCrudException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ConversationCrudException(String message) {
      super(message);
    }
  }
}