package com.bside.bside_311.util;

import com.bside.bside_311.entity.BaseCreatedByGetter;
import com.bside.bside_311.entity.Role;
import com.bside.bside_311.model.AbstractUserAuthInfo;
import org.springframework.util.ObjectUtils;

public class ValidateUtil {
  /**
   * Checks if the resource created by a specific user can be modified by the current user based on their authentication information.
   * This method ensures that only the creator of a resource or an administrator can make changes to it.
   *
   * @param base An object that implements {@link BaseCreatedByGetter} interface, typically representing the resource
   *             that needs to be checked for modification permissions.
   * @param userAuthInfo An object of type {@link AbstractUserAuthInfo}, representing the current user's authentication information.
   *
   * @throws IllegalArgumentException if any of the following conditions are met:
   *         - The {@code base} object is null, or the creator of the {@code base} object (obtained via {@code getCreatedBy()}) is null.
   *         - The {@code userAuthInfo} object is null.
   *         - The current user is neither the creator of the resource nor an administrator.
   *           (Administrators are identified by having the {@link Role.ROLE_ADMIN} authority.)
   *
   * <p> If the current user is an administrator (i.e., has the {@link Role.ROLE_ADMIN} authority), they are allowed to modify any resource.
   * If the current user is the same as the creator of the resource, they are also allowed to make changes.
   * This check is vital for maintaining the integrity and security of user-created resources.
   * </p>
   */
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
