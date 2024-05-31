package com.hotelking.dto.request;

import com.hotelking.dto.RequestDtoCheckable;
import com.hotelking.dto.auth.ConfirmAuthDto;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import lombok.Builder;
import org.apache.commons.lang3.ObjectUtils;

@Builder
public record PhoneAuthConfirmRequest(
    String token,
    String phoneNumber,
    String authCode
) implements RequestDtoCheckable {

  public ConfirmAuthDto toDto() {
    return ConfirmAuthDto.builder()
        .phoneNumber(phoneNumber)
        .authCode(authCode)
        .token(token)
        .build();
  }

  @Override
  public void validationCheck() {
    if (ObjectUtils.anyNull(token, phoneNumber, authCode)) {
      throw new HotelkingException(ErrorCode.USER_AUTH_ARGUMENT, null);
    }
  }
}
