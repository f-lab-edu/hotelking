package com.hotelking.api.dto.request;

import com.hotelking.api.common.RequestDtoCheckable;
import com.hotelking.global.exception.ErrorCode;
import com.hotelking.global.exception.HotelkingException;

public record AccessTokenIssueRequest(String refreshToken) implements RequestDtoCheckable {

  @Override
  public void validationCheck() {
    if (refreshToken == null || refreshToken.isBlank()) {
      throw new HotelkingException(ErrorCode.JWT_TOKEN_NOT_VALID, null);
    }
  }
}
