package com.bside.bside_311.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TestUtilTest {

  @Test
  void sum() {
    // given
    // when
    int sum = TestUtil.sum(1, 2);
    // then
    assertEquals(3, sum);
  }

  @Test
  void sumNonStatic() {
    // given
    // when
    TestUtil testUtil = new TestUtil();
    int sum = testUtil.sumNonStatic(1, 2);
    // then
    assertEquals(3, sum);
  }
}