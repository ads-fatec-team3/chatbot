package br.gov.sp.fatec.backend.exceptions;

public class MessageException {
  public static class MessageNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MessageNotFoundException(long messageId) {
      super(String.format("mensagem de id = %d não encontrada", messageId));
    }
  }

  public static class MessageCrudException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MessageCrudException(String message) {
      super(message);
    }
  }
}