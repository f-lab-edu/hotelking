package com.hotelking.global.exception;

import java.util.Arrays;
import lombok.Getter;

@Getter
public enum ErrorCode {
  HOTEL_ADMIN_01("HOTEL_ADMIN_01", "위도는 33 ~ 43 사이어야합니다."),
  HOTEL_ADMIN_02("HOTEL_ADMIN_02", "경도는 124 ~ 132 사이어야합니다."),
  HOTEL_ADMIN_03("HOTEL_ADMIN_03", "호텔이름(hotelName) 는 필수입니다."),
  HOTEL_ADMIN_04("HOTEL_ADMIN_04", "호텔주소(hotelAddress) 는 필수입니다."),

  NOT_DEFINED("NOT_DEFINED", "");

  private final String code;
  private final String message;

  ErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public static ErrorCode findCode(String code) {
    return Arrays.stream(ErrorCode.values()).filter(it -> it.getCode().equals(code))
        .findFirst()
        .orElse(NOT_DEFINED);
  }
}
