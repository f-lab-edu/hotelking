package com.hotelking.dto.request;

import com.hotelking.domain.user.vo.AgreementName;
import com.hotelking.dto.AddUserDto;
import com.hotelking.dto.AgreementNames;
import java.util.Set;

public record AddUserRequest(
    String userId,
    String password,
    String phoneNumber,
    String token,
    Set<AgreementName> agreementNames
) {

  public AddUserDto toAddUserDto() {
    return AddUserDto.builder()
        .userId(userId)
        .password(password)
        .phoneNumber(phoneNumber)
        .build();
  }

  public AgreementNames toAgreementNames() {
    return AgreementNames.builder()
        .agreementNames(agreementNames)
        .build();
  }
}
