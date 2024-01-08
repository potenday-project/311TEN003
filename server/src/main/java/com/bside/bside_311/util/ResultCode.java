package com.bside.bside_311.util;

public enum ResultCode {
  SUCCESS("RES000", "success"),
  FAIL("RES999", "fail"),
  UNAUTHORIZED("RES998", "unauthorized"),
  NOT_FOUND("404", "not found"),
  INTERNAL_SERVER_ERROR("RES997", "internal server error");

  private final String code;
  private final String message;

  ResultCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String getCode() {
    return this.code;
  }

  public String getMessage() {
    return this.message;
  }
}
