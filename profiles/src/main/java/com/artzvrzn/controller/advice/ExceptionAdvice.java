package com.artzvrzn.controller.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class ExceptionAdvice {

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<ResponseError> illegalArgumentHandler(IllegalArgumentException exc) {
    log.error(exc);
    return new ResponseEntity<>(new ResponseError(exc.getMessage()), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ResponseError> exceptionHandler(Exception exc) {
    log.error(exc);
    return new ResponseEntity<>(
      new ResponseError(exc.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @Getter
  @Setter
  @RequiredArgsConstructor
  public static class ResponseError {
    private final String logref = "error";
    private final String message;
  }
}
