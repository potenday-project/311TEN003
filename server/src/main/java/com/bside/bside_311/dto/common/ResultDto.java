package com.bside.bside_311.dto.common;

import com.bside.bside_311.util.ResultCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(staticName = "of")
public class ResultDto<D> {
  private final String resultCode;
  private final String message;
  private final D data;

  public static <D> ResultDto<D> of(ResultCode resultCode, D data) {
    return new ResultDto<D>(resultCode.getCode(), resultCode.getMessage(), data);
  }

  public static <D> ResultDto<D> successOf(D data) {
    return new ResultDto<D>(ResultCode.SUCCESS.getCode(), ResultCode.SUCCESS.getMessage(), data);
  }
}
