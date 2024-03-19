package com.bside.bside_311.etc;

public class LayzInitTest {
  private FieldType field;

  public static FieldType getFiled() {
    return FieldHolder.field;
  }

  public static void main(String[] args) {
    System.out.println("test start");
    FieldType filed = LayzInitTest.getFiled();
    System.out.println("test end");
  }

  public static class FieldHolder {
    static final FieldType field = computeFieldValue();

    private static FieldType computeFieldValue() {
      System.out.println("computeFieldValue");
      return new FieldType("test");
    }
  }

}
