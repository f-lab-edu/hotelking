package com.hotelking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  EXCEED_LAT("A001", "잘못된 위치정보입니다.","위도는 33 ~ 43 사이어야합니다.", HttpStatus.BAD_REQUEST),
  EXCEED_LNG("A002", "잘못된 위치정보입니다.","경도는 124 ~ 132 사이어야합니다.", HttpStatus.BAD_REQUEST),
  REQ_HOTEL_NAME("A003", "입력정보가 부정확합니다.","호텔이름(hotelName) 는 필수입니다.", HttpStatus.BAD_REQUEST),
  REQ_HOTEL_ADDR("A004", "입력정보가 부정확합니다.","호텔주소(hotelAddress) 는 필수입니다.", HttpStatus.BAD_REQUEST),

  NOT_READABLE("C001", "잘못된 요청값입니다.", "잘못된 요청값입니다.",  HttpStatus.BAD_REQUEST),
  NOT_DEFINED("X001", "", "", HttpStatus.INTERNAL_SERVER_ERROR);

  private final String code;
  private final String externalErrorMessage;
  private final String internalErrorMsg;
  private final HttpStatus httpStatus;

  ErrorCode(String code, String externalErrorMessage, String internalErrorMsg, HttpStatus httpStatus) {
    this.code = code;
    this.externalErrorMessage = externalErrorMessage;
    this.internalErrorMsg = internalErrorMsg;
    this.httpStatus = httpStatus;
  }
}
