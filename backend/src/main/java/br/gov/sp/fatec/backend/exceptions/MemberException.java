package br.gov.sp.fatec.backend.exceptions;

public class MemberException {
  public static class MemberNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MemberNotFoundException(long memberId) {
      super(String.format("membro de id = %d não encontrado", memberId));
    }
  }

  public static class MemberCrudException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MemberCrudException(String message) {
      super(message);
    }
  }
}