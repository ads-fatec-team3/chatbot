package br.gov.sp.fatec.backend.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;

public class GrulyApiException {
  private HttpStatus status;
  private String message;
  private List<String> errors = new ArrayList<String>();

  public GrulyApiException(HttpStatus status, String message, String error) {
    this.status = status;
    this.message = message;
    this.errors.add(error);
  }

  public GrulyApiException(HttpStatus status, String message, List<String> errors) {
    this.status = status;
    this.message = message;
    this.errors = errors;
  }

  public HttpStatus getStatus() {
    return status;
  }
  
  public String getMessage()  {
    return message;
  }

  public List<String> getErrors() {
    return errors;
  }
}