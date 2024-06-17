package com.hotelking.dto.request;

import com.hotelking.dto.RequestDtoCheckable;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;

public record AccessTokenIssueRequest(String refreshToken) implements RequestDtoCheckable {

  @Override
  public void validationCheck() {
    if (refreshToken == null || refreshToken.isBlank()) {
      throw new HotelkingException(ErrorCode.JWT_TOKEN_NOT_VALID, null);
    }
  }
}
