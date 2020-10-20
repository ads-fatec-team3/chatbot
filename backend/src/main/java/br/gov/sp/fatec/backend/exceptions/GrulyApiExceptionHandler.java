package br.gov.sp.fatec.backend.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.gov.sp.fatec.backend.exceptions.ConversationException.ConversationCrudException;
import br.gov.sp.fatec.backend.exceptions.ConversationException.ConversationNotFoundException;
import br.gov.sp.fatec.backend.exceptions.MemberException.MemberCrudException;
import br.gov.sp.fatec.backend.exceptions.MemberException.MemberNotFoundException;
import br.gov.sp.fatec.backend.exceptions.MessageException.MessageCrudException;
import br.gov.sp.fatec.backend.exceptions.MessageException.MessageNotFoundException;

@ControllerAdvice
public class GrulyApiExceptionHandler extends ResponseEntityExceptionHandler {
  GrulyApiException apiException;
 
  @Override
  public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
                                                                     HttpHeaders headers, HttpStatus status,
                                                                     WebRequest request) {
    String error = String.format("falta o parâmetro %s", ex.getParameterName());

    apiException = new GrulyApiException(HttpStatus.BAD_REQUEST, "parâmetro necessário ausente", error);
    
    return new ResponseEntity<Object>(apiException, new HttpHeaders(), apiException.getStatus());
  }

  @ExceptionHandler({MemberNotFoundException.class, ConversationNotFoundException.class, MessageNotFoundException.class})
  public ResponseEntity<Object> handleEntityNotFound(Exception ex, WebRequest request) {
    apiException = new GrulyApiException(HttpStatus.BAD_REQUEST, "entidade não encontrada", ex.getMessage());
    
    return new ResponseEntity<Object>(apiException, new HttpHeaders(), apiException.getStatus());
  }

  @ExceptionHandler({MemberCrudException.class, ConversationCrudException.class, MessageCrudException.class})
  public ResponseEntity<Object> handleEntityCrudError(Exception ex, WebRequest request) {
    apiException = new GrulyApiException(HttpStatus.INTERNAL_SERVER_ERROR, "erro na manipulação de dados", ex.getMessage());
    
    return new ResponseEntity<Object>(apiException, new HttpHeaders(), apiException.getStatus());
  }
}