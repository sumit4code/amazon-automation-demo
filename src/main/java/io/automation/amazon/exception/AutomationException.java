package io.automation.amazon.exception;

public class AutomationException extends RuntimeException {

  public AutomationException(String message, Throwable throwable){
    super(message, throwable);
  }
}
