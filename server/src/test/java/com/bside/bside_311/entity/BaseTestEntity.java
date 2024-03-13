package com.bside.bside_311.entity;

public class BaseTestEntity implements BaseCreatedByGetter {
  private Long createdBy;

  public BaseTestEntity(Long createdBy) {
    this.createdBy = createdBy;
  }

  @Override
  public Long getCreatedBy() {
    return createdBy;
  }

  public BaseTestEntity from(long createdBy) {
    return new BaseTestEntity(createdBy);
  }
}
