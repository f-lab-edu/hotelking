package com.hotelking.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
  EXCEED_LAT("A001", "잘못된 위치정보입니다.","위도는 33 ~ 43 사이어야합니다.", HttpStatus.BAD_REQUEST),
  EXCEED_LNG("A002", "잘못된 위치정보입니다.","경도는 124 ~ 132 사이어야합니다.", HttpStatus.BAD_REQUEST),
  REQ_HOTEL_NAME("A003", "입력정보가 부정확합니다.","호텔이름(hotelName) 는 필수입니다.", HttpStatus.BAD_REQUEST),
  REQ_HOTEL_ADDR("A004", "입력정보가 부정확합니다.","호텔주소(hotelAddress) 는 필수입니다.", HttpStatus.BAD_REQUEST),

  // Order, Reservation
  ROOM_SCHEDULE_FULL("R001", "예약할 수 있는 방이 존재하지 않습니다. 다른 방을 예약하세요", "ROOM_SCHEDULE 이 없어서 예약 불가", HttpStatus.BAD_REQUEST),
  ROOM_ORDER_FULL("R002", "예약할 수 있는 방이 존재하지 않습니다. 다른 방을 예약하세요", "RESERVATION 되지 않은 ORDER 로 인해 예약 불가", HttpStatus.BAD_REQUEST),
  NOT_FOUND_ROOM_TYPE("R003", "예약할 수 있는 방이 존재하지 않습니다. 다른 방을 예약하세요", "Room Type not found Error, Maybe Admin delete room type", HttpStatus.BAD_REQUEST),
  REQ_ORDER_DAESIL("R004", "잘못된 예약 정보 입력입니다.", "날짜가 다른 대실 예약 정보 입력 불가", HttpStatus.BAD_REQUEST),
  REQ_ORDER_STAY("R005", "잘못된 예약 정보 입력입니다.", "날짜가 같은 숙박 예약 정보 입력 불가(최소 1박 이상)", HttpStatus.BAD_REQUEST),
  REQ_ORDER_DATE("R006", "잘못된 예약 정보 입력입니다.", "체크인은 체크아웃보다 먼저 와야한다.", HttpStatus.BAD_REQUEST),

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
