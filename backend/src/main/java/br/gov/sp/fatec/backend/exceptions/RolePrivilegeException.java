package br.gov.sp.fatec.backend.exceptions;

public class RolePrivilegeException {
  public static class RolePrivilegeNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public RolePrivilegeNotFoundException(long rolePrivilegeId) {
      super(String.format("privilégio de permissão de id = %d não encontrado", rolePrivilegeId));
    }
  }
}