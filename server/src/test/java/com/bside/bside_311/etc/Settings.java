package com.bside.bside_311.etc;

public class Settings {

  private static final String SETTINGS_INSTANCE = "Settings Instance";

  static {
    System.out.println(
        "1 - Outer Class static initializer SETTINGS_INSTANCE = " + SETTINGS_INSTANCE);
  }

  private Settings() {
    System.out.println("4 - Constructor Settings");
  }

  public static void main(String[] args) {
    System.out.println("2 - main SETTINGS_INSTANCE = " + SETTINGS_INSTANCE);
    System.out.println("3 - SettingsHolder.class = " + SettingsHolder.class);
    System.out.println(
        "6 - SettingsHolder.SETTINGS_IN_HOLDER " + SettingsHolder.SETTINGS_IN_HOLDER);
  }

  private static class SettingsHolder {

    private static final Settings SETTINGS_IN_HOLDER = new Settings();

    static {
      System.out.println("5 - SettingsHolder.static initializer");
    }

    private SettingsHolder() {
      System.out.println("Constructor SettingsHolder");
    }
  }
}