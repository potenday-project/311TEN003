package com.bside.bside_311.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerConfig {

  @ExceptionHandler({IllegalArgumentException.class, BadCredentialsException.class})
  @ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
  public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e) {
    return errorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), e.getMessage());
  }

  private ErrorResponse errorResponse ( int status, String errorMessage, String detailMessage ) {
    return new ErrorResponse( status, errorMessage, detailMessage );
  }

}
