package com.hotelking.exception;

import lombok.Getter;

@Getter
public class HotelkingException extends RuntimeException{
  private final ErrorCode errorCode;

  public HotelkingException(ErrorCode errorCode) {
    this.errorCode = errorCode;
  }

}
