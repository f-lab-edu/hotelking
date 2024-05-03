package com.hotelking.global;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hotelking.global.exception.ErrorCode;

@JsonInclude(value = Include.NON_NULL)
public record ErrorContent(
    String code,
    String message,
    String field
) {

  public static ErrorContent from(ErrorCode errorCode) {
    return new ErrorContent(errorCode.name(), errorCode.getMessage(), null);
  }

  public static ErrorContent of(ErrorCode errorCode, String field) {
    return new ErrorContent(errorCode.name(), errorCode.getMessage(), field);
  }

}
