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
  REV_NO_SCHEDULE("R007", "비어있는 스케줄이 없습니다. 다른 방을 선택해주세요", "비어있는 스케줄이 없습니다. 다른 방을 선택해주세요", HttpStatus.BAD_REQUEST),
  REV_ALREADY_SCHEDULED("R008", "해당 예약은 스케줄을 설정할 수 없습니다.", "해당 주문은 이미 스케줄이 존재합니다.", HttpStatus.BAD_REQUEST),
  REV_NOT_FOUND("R010", "해당되는 예약을 찾을 수 없습니다.", "해당되는 예약을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST),

  // User
  USER_AUTH_PHONE_CODE("U001", "인증번호는 필수입력 정보입니다.", "인증번호는 필수입력 정보입니다.", HttpStatus.BAD_REQUEST),
  USER_AUTH_PHONE_FORMAT_1("U002", "올바르지 못한 인증 정보 입니다.", "잘못된 인증번호 포맷(6자리의 숫자)", HttpStatus.BAD_REQUEST),
  USER_AUTH_PHONE_NUMBER("U003", "휴대전화는 필수입력 정보입니다.", "휴대전화는 필수입력 정보입니다.", HttpStatus.BAD_REQUEST),
  USER_AUTH_PHONE_CONFIRM_NOTFOUND("U004", "인증번호가 일치하지 않습니다.", "존재하지 않는 인증번호 코드", HttpStatus.BAD_REQUEST),
  USER_AUTH_PHONE_CONFIRM_TIMEOVER("U005", "시간초과 되었습니다. 다시 인증번호 발급을 요청하시기 바랍니다.", "시간초과된 인증번호 코드", HttpStatus.BAD_REQUEST),
  USER_AUTH_PHONE_CONFIRM_VERIFIED("U006", "이미 휴대폰 인증이 완료되었습니다.", "인증된 코드에 중복 요청", HttpStatus.BAD_REQUEST),
  USER_AUTH_PHONE_CONFIRM_NOT_SAME("U007", "인증번호가 일치하지 않습니다.", "존재하지만 잘못된 인증번호 요청", HttpStatus.BAD_REQUEST),
  USER_AUTH_PHONE_CONFIRM_DECRYPT_ERROR("U008", "인증번호가 일치하지 않습니다.", "휴대폰 인증 토큰 에러", HttpStatus.BAD_REQUEST),
  USER_AUTH_ARGUMENT("U009", "인증번호가 일치하지 않습니다.", "필드는 null 일 수 없습니다.", HttpStatus.BAD_REQUEST),

  // User
  USER_INVALID_PARAM_ID("U010", "유저의 입력정보가 부정확합니다.", "userId 는 필수입니다.", HttpStatus.BAD_REQUEST),
  USER_INVALID_PARAM_PWD("U011", "유저의 입력정보가 부정확합니다.", "password 는 필수입니다.", HttpStatus.BAD_REQUEST),
  USER_INVALID_PARAM_PHONE("U012", "잘못된 휴대폰 번호 양식입니다.", "phoneNumber 는 필수입니다.", HttpStatus.BAD_REQUEST),
  USER_DUPLICATED_ID("U013", "이미 사용 중인 아이디입니다.", "이미 사용 중인 아이디입니다.", HttpStatus.BAD_REQUEST),
  USER_NOT_VERIFIED_PHONE("U014", "휴대폰 인증은 필수입니다.", "인증 하지 않고 회원 가입 요청 시도", HttpStatus.BAD_REQUEST),
  USER_NOT_REQUIRED_TERM("U015", "필수 약관에 동의해야합니다.", "서비스에 필요로 하는 약관에 동의 하지 않음", HttpStatus.BAD_REQUEST),
  USER_INVALID_PARAM_PWD_CONFIRM("U016", "비밀번호와 비밀번호확인이 일치하지 않습니다.", "비밀번호와 비밀번호확인이 일치하지 않습니다.", HttpStatus.BAD_REQUEST),

  USER_ID_NOT_FOUND("U019", "계정정보가 일치하지 않습니다. 다시 로그인 바랍니다.", "존재하지 않는 유저 아이디 입력", HttpStatus.BAD_REQUEST),
  USER_PASSWORD_NOT_MATCH("U018", "계정정보가 일치하지 않습니다. 다시 로그인 바랍니다.", "잘못된 비밀번호 입력", HttpStatus.BAD_REQUEST),
  USER_FIND_ID_NOT_FOUND_PHONE("U017", "존재하지 않는 유저입니다.", "존재하지 않는 휴대전화 번호로 아이디 찾기 시도", HttpStatus.BAD_REQUEST),

  JWT_RT_TOKEN_EXPIRE("U018", "로그인 기간 만료되었습니다. 다시 로그인 하시기 바랍니다.", "존재 하지않는 RT 로 요청", HttpStatus.BAD_REQUEST),
  JWT_TOKEN_NOT_VALID("U019", "유효하지 않은 토큰입니다.", "유효하지 않은 토큰입니다.", HttpStatus.BAD_REQUEST),
  USER_NOT_FOUND("U020", "잘못된 요청입니다. ", "찾을 수 없는 사용자", HttpStatus.BAD_REQUEST),

  // authorization
  AUTH_JWT_NUL("AU001", "인증이 필요한 요청입니다.", "인증 헤더(Authorization) 는 필수입니다. ", HttpStatus.UNAUTHORIZED),
  AUTH_JWT_DELETE_USER("AU002", "잘못된 요청입니다.", "탈퇴 혹은 존재하지 않은 유저의 토큰으로 요청 시도", HttpStatus.BAD_REQUEST),

  NOT_READABLE("C001", "잘못된 요청값입니다.", "잘못된 요청값입니다.",  HttpStatus.BAD_REQUEST),
  NOT_DEFINED("X001", "", "", HttpStatus.INTERNAL_SERVER_ERROR),

  // price
  PRICE_MIN("P001", "기본 룸 가격은 1만원 이상 설정 해야합니다.", "가격은 1만원 이상 설정해야한다.", HttpStatus.BAD_REQUEST),
  PRICE_DISCOUNT_MIN("P002", "기본 할인 가격은 최소 1000원 이상 이어야 합니다.", "최소 할인 금액(1000) 이상 필수", HttpStatus.BAD_REQUEST),
  PRICE_TOTAL_MIN("P003", "룸 가격 - 할인 금액은 최소 1000원 이상이어야 합니다.", "최종 룸 가격은 1000원 이상 필수", HttpStatus.BAD_REQUEST),
  PRICE_BASE_MONEY_REQUIRED("P004", "룸 기준 가격은 필수 입력 조건입니다.", "룸 기준 가격은 Null 일 수 없음", HttpStatus.BAD_REQUEST),
  PRICE_BUILD_ROOM_TYPE("P005", "룸 가격 등록 시 룸 타입에 대한 정보는 필수입니다.", "RoomType 은 Null 일 수 없음", HttpStatus.BAD_REQUEST),
  PRICE_NOT_NULL("P006", "룸 가격 설정 시 가격은 필수 입력 조건입니다.", "MoneyAndDiscount 의 price Money 는 Null 일 수 없음", HttpStatus.BAD_REQUEST),
  PRICE_DISCOUNT_NOT_NEGATIVE("P007", "잘못된 룸 할인 금액입니다.", "RoomDiscount 금액이 마이너스(-) 일 수 없음", HttpStatus.BAD_REQUEST),
  PRICE_DEFAULT_REQUIRED("P008", "기본 가격은 필수 입력 조건입니다.", "defaultPrice 는 필수", HttpStatus.BAD_REQUEST),
  PRICE_CUSTOM_KEY_ERROR("P009", "커스텀 가격 에러 발생", "customPrice key 는 yyyy-MM-dd 포맷 필수", HttpStatus.BAD_REQUEST),
  PRICE_DAY_NOT_NULL("P010", "가격 적용 요일은 필수 입력 조건입니다.", "RoomPriceWeekType 의 day 는 Null 일 수 없음", HttpStatus.BAD_REQUEST),
  PRICE_PRICES_REQUIRED("P011", "각 요일의 가격 정보는 필수 입력 조건입니다.", "각 요일 당 최소 하나의 RoomPriceElement 는 필수", HttpStatus.BAD_REQUEST),
  PRICE_CUSTOM_DEFAULT_REQUIRED("P012", "커스텀 가격의 경우 기본 가격정보는 필수 입력 조건입니다.", "CustomPrice.defaultPrice 는 필수", HttpStatus.BAD_REQUEST),
  PRICE_WEEKS_OF_DAY_REQUIRED("P013", "룸 타입 가격설정 시 요일 정보는 필수 입력 조건입니다.", "RoomPriceWeekday 는 필수", HttpStatus.BAD_REQUEST),
  PRICE_ALL_DAY_REQUIRED("P014", "룸 타입 가격 설정시 모든 요일 정보(월 ~ 일, 공휴일)는 필수 입력 조건입니다.", "RoomPriceWeekType 에 있는 모든 요일 정보 필수", HttpStatus.BAD_REQUEST),
  PRICE_ROOM_TYPE_PRICE_NOT_NULL("P015", "룸 타입과 가격정보는 필수 입력 조건 입니다.", "RoomType, RoomPrice 는 필수", HttpStatus.BAD_REQUEST),

  // search
  SEARCH_HOTEL_ID("S001", "호텔 번호는 필수입니다.", "호텔 정보(hotelId)는 필수", HttpStatus.BAD_REQUEST),
  SEARCH_CHECK_IN("S002", "체크인 날짜정보는 필수 입력 조건입니다.", "체크인 날짜는 Null 일 수 없음", HttpStatus.BAD_REQUEST),
  SEARCH_DATE_FORMAT("S003", "날짜 포맷이 올바르지 않습니다.(yyyy-MM-dd)", "올바른 날짜 포맷 필수(yyyy-MM-dd)", HttpStatus.BAD_REQUEST),
  SEARCH_DATE_ORDER("S004", "체크인, 체크아웃 순서 에러", "올바른 체크인, 체크아웃 날짜 순서 입력 필수", HttpStatus.BAD_REQUEST),
  SEARCH_TYPE_REQUIRED("S005", "검색 타입 에러", "검색 타입 입력 필수", HttpStatus.BAD_REQUEST);

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
