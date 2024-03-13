package com.bside.bside_311.util;

import com.bside.bside_311.entity.BaseCreatedByGetter;
import com.bside.bside_311.entity.Role;
import com.bside.bside_311.model.AbstractUserAuthInfo;
import org.springframework.util.ObjectUtils;

public class ValidateUtil {

  public static void resourceChangeableCheckByThisUserAuthInfo(BaseCreatedByGetter base,
                                                               AbstractUserAuthInfo userAuthInfo) {
    if (ObjectUtils.isEmpty(base) || ObjectUtils.isEmpty(base.getCreatedBy())) {
      throw new IllegalArgumentException("본인이 작성한 리소스만 변경 가능합니다.");
    }
    if (ObjectUtils.isEmpty(userAuthInfo)) {
      throw new IllegalArgumentException("본인이 작성한 리소스만 변경 가능합니다.");
    }
    if (userAuthInfo.getAuthorities().stream().anyMatch(o -> o == (Role.ROLE_ADMIN))) {
      // 관리자는 모든 리소스에 대해 변경 가능
      return;
    }
    if (!base.getCreatedBy().equals(userAuthInfo.getId())) {
      throw new IllegalArgumentException("본인이 작성한 리소스만 변경 가능합니다.");
    }
  }
}
