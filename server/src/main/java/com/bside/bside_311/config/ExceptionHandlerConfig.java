package com.bside.bside_311.config;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Slf4j
@RestControllerAdvice
public class ExceptionHandlerConfig {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";

  public static final String ANSI_WHITE = "\u001B[37m";

  public static final String RED_UNDERLINED = "\033[4;31m";

  @ExceptionHandler({IllegalArgumentException.class, BadCredentialsException.class})
  @ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
  public ErrorResponse handleIllegalArgumentException(IllegalArgumentException e,
                                                      HandlerMethod method,
                                                      HttpServletRequest request) {
    exactErrorLog(e, method, BAD_REQUEST, request);
    return errorResponse(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(),
        e.getMessage());
  }

  @ExceptionHandler({IOException.class})
  @ResponseStatus(value = org.springframework.http.HttpStatus.BAD_REQUEST)
  public ErrorResponse handleIOException(IllegalArgumentException e, HandlerMethod method,
                                         HttpServletRequest request) {
    exactErrorLog(e, method, BAD_REQUEST, request);
    return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e.getMessage());
  }

  @ExceptionHandler(AuthenticationServiceException.class)
  @ResponseStatus(UNAUTHORIZED)
  public ErrorResponse handleAuthorizationException(AuthenticationServiceException e,
                                                    HandlerMethod method,
                                                    HttpServletRequest request) {
    return errorResponse(UNAUTHORIZED.value(), UNAUTHORIZED.getReasonPhrase(), e.getMessage());
  }

  private ErrorResponse errorResponse(int status, String errorMessage, String detailMessage) {
    return new ErrorResponse(status, errorMessage, detailMessage);
  }

  private void exactErrorLog(Exception e, HandlerMethod handlerMethod, HttpStatus httpStatus,
                             HttpServletRequest request) {
    String errorDate = ANSI_YELLOW + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(
        Calendar.getInstance().getTime()) + ANSI_RESET;
    String requestURI = ANSI_CYAN + request.getRequestURI() + ANSI_RESET;
    String exceptionName = ANSI_PURPLE + e.getClass().getSimpleName() + ANSI_RESET;
    String status = ANSI_RED + httpStatus.toString() + ANSI_RESET;
    String controllerName =
        ANSI_GREEN + handlerMethod.getMethod().getDeclaringClass().getSimpleName() + ANSI_RESET;
    String methodName = ANSI_BLUE + handlerMethod.getMethod().getName() + ANSI_RESET;
    String message = ANSI_RED + e.getMessage() + ANSI_RESET;
    String lineNumber = RED_UNDERLINED + e.getStackTrace()[0].getLineNumber() + ANSI_RESET;
    log.error(
        "\n[Time: {} | Class: {} | Method: {} | LineNumber: {} | Path: {} | Exception: {} | Status: {} | Message: {}]\n",
        errorDate, controllerName, methodName, lineNumber, requestURI, exceptionName, status,
        message);
    e.printStackTrace();
  }

}
