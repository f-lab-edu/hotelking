package com.hotelking.global.exception;

import lombok.Getter;
import org.slf4j.Logger;

@Getter
public class HotelkingException extends RuntimeException{
  private final ErrorCode errorCode;
  private final Logger logger;

  public HotelkingException(ErrorCode errorCode, Logger logger) {
    this.errorCode = errorCode;
    this.logger = logger;
  }

}
