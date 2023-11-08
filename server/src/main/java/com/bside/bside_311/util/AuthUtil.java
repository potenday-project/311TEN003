package com.bside.bside_311.util;

import org.springframework.security.core.context.SecurityContextHolder;

public class AuthUtil {
  public static Long getUserNoFromAuthentication() {
    return Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
  }
}
