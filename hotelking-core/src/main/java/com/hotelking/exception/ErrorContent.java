package com.hotelking.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(value = Include.NON_NULL)
public record ErrorContent(String code, String message) {

  public static ErrorContent from(ErrorCode errorCode) {
    return new ErrorContent(errorCode.getCode(), errorCode.getExternalErrorMessage());
  }

  public static ErrorContent createOnlyMessage(String message) {
    return new ErrorContent(null, message);
  }

}