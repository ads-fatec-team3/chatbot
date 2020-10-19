package br.gov.sp.fatec.backend.utils;

import java.util.ArrayList;
import java.util.List;

public class GrulyApiExceptionResponse<T> {
  private T data;
  private List<String> errorMessages = new ArrayList<String>();

  
  public T getData() {
    return data;
  }
  
  public List<String> getErrorMessages() {
    return errorMessages;
  }
  
  public void setData(T data) {
    this.data = data;
  }

  public void setErrorMessages(List<String> errorMessages) {
    this.errorMessages = errorMessages;
  }

  public void addErrorMessage(String message) {
    this.errorMessages.add(message);
  }
}