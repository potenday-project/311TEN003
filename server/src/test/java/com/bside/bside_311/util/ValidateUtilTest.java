package com.bside.bside_311.util;

import com.bside.bside_311.entity.BaseCreatedByGetter;
import com.bside.bside_311.entity.BaseTestEntity;
import com.bside.bside_311.entity.Role;
import com.bside.bside_311.model.TestUserAuthInfo;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ValidateUtilTest {

  @Test
  void resourceChangeableCheckByThisUserAuthInfo() {
    // Given
    BaseCreatedByGetter base = new BaseTestEntity(2L);
    TestUserAuthInfo userAuthInfo = new TestUserAuthInfo(2L, List.of(Role.ROLE_USER));
    // When    // Then
    assertDoesNotThrow(
        () -> ValidateUtil.resourceChangeableCheckByThisUserAuthInfo(base, userAuthInfo));
  }

  @Test
  void resourceChangeableCheckByThisUserAuthInfo_success() {
    // Given
    BaseCreatedByGetter base = new BaseTestEntity(2L);
    TestUserAuthInfo userAuthInfo = new TestUserAuthInfo(3L, List.of(Role.ROLE_ADMIN));
    // When    // Then
    assertDoesNotThrow(
        () -> ValidateUtil.resourceChangeableCheckByThisUserAuthInfo(base, userAuthInfo));
  }

  @Test
  void resourceChangeableCheckByThisUserAuthInfo_fail_noRole() {
    // Given
    BaseCreatedByGetter base = new BaseTestEntity(2L);
    TestUserAuthInfo userAuthInfo = new TestUserAuthInfo(3L, List.of());
    // When    // Then
    assertThrows(IllegalArgumentException.class,
        () -> ValidateUtil.resourceChangeableCheckByThisUserAuthInfo(base, userAuthInfo));
  }

  @Test
  void resourceChangeableCheckByThisUserAuthInfo_fail_notItsResource() {
    // Given
    BaseCreatedByGetter base = new BaseTestEntity(2L);
    TestUserAuthInfo userAuthInfo = new TestUserAuthInfo(3L, List.of(Role.ROLE_USER));
    // When    // Then
    assertThrows(IllegalArgumentException.class,
        () -> ValidateUtil.resourceChangeableCheckByThisUserAuthInfo(base, userAuthInfo));
  }
}