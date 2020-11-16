package br.gov.sp.fatec.backend.exceptions;

public class MemberRoleException {
  public static class MemberRoleNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MemberRoleNotFoundException(long memberRoleId) {
      super(String.format("permissão de membro de id = %d não encontrada", memberRoleId));
    }
  }
}