package com.hotelking.dto.request;

import static com.hotelking.exception.ErrorCode.USER_INVALID_PARAM_PWD_CONFIRM;

import com.hotelking.dto.AddUserDto;
import com.hotelking.dto.RequestDtoCheckable;
import com.hotelking.dto.TermIdsDto;
import com.hotelking.exception.HotelkingException;
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