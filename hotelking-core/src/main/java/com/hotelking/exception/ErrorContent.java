package com.hotelking.exception;

public record ErrorContent(
    String code,
    String message
) {

  public static ErrorContent from(ErrorCode errorCode) {
    return new ErrorContent(errorCode.getCode(), errorCode.getExternalErrorMessage());
  }

}