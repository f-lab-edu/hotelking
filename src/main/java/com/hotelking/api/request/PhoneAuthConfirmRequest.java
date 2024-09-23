package com.hotelking.api.request;

import com.hotelking.api.common.RequestDtoCheckable;
import com.hotelking.api.dto.ConfirmAuthDto;
import com.hotelking.global.exception.ErrorCode;
import com.hotelking.global.exception.HotelkingException;
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
