package com.bside.bside_311.util;

import com.bside.bside_311.entity.BaseEntity;
import com.bside.bside_311.entity.Role;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

import java.util.Collection;

public class ValidateUtil {

  public static void resourceChangeableCheckByThisRequestToken(BaseEntity base) {
    if (ObjectUtils.isEmpty(base) || ObjectUtils.isEmpty(base.getCreatedBy())) {
      throw new IllegalArgumentException("본인이 작성한 리소스만 변경 가능합니다.");
    }
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (ObjectUtils.isEmpty(authentication)) {
      throw new IllegalArgumentException("본인이 작성한 리소스만 변경 가능합니다.");
    }
    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
    if (authorities.stream().anyMatch(o -> o.getAuthority().equals(Role.ROLE_ADMIN.name()))) {
      // 관리자는 모든 리소스에 대해 변경 가능
      return;
    }
    if (!base.getCreatedBy().equals(Long.parseLong(authentication.getName()))) {
      throw new IllegalArgumentException("본인이 작성한 리소스만 변경 가능합니다.");
    }
  }
}
