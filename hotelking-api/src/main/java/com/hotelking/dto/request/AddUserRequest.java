package com.hotelking.dto.request;

import com.hotelking.dto.AddUserDto;
import com.hotelking.dto.TermIdsDto;
import java.util.Set;
import java.util.stream.Collectors;

public record AddUserRequest(
    String userId,
    String password,
    String phoneNumber,
    String token,
    Set<AgreementTerm> agreementOfTerms
) {

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
}