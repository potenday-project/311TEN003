package com.bside.bside_311.entity;

public enum AttachType {
  PROFILE("PROFILE"),
  POST("POST"),
  ALCOHOL("ALCOHOL");

  private final String value;

  AttachType(String value) {
    this.value = value;
  }

  public String toString() {
    return value;
  }
}