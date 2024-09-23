package com.hotelking.api.dto.request;

import static com.hotelking.global.exception.ErrorCode.USER_INVALID_PARAM_PWD_CONFIRM;

import com.hotelking.api.common.RequestDtoCheckable;
import com.hotelking.api.dto.AddUserDto;
import com.hotelking.api.dto.TermIdsDto;
import com.hotelking.global.exception.HotelkingException;
import java.util.Set;
import java.util.stream.Collectors;

public record AddUserRequest(
    String userId,
    String password,
    String passwordConfirm,
    String phoneNumber,
    String token,
    Set<AgreementTerm> agreementOfTerms
) implements RequestDtoCheckable {

  public AddUserDto toAddUserDto() {
    return AddUserDto.builder()
        .userId(userId)
        .password(password)
        .phoneNumber(phoneNumber)
        .build();
  }

  public TermIdsDto toTermIdsDto() {
    return TermIdsDto.builder()
        .termIdDtoSet(
            agreementOfTerms.stream()
                .map(AgreementTerm::toTermIdDto)
                .collect(Collectors.toSet()))
        .build();
  }

  @Override
  public void validationCheck() {
    if (!password.equals(passwordConfirm)) {
      throw new HotelkingException(USER_INVALID_PARAM_PWD_CONFIRM, null);
    }
  }
}