package com.hotelking.api.request;

import com.hotelking.api.dto.TermIdDto;
import com.hotelking.domain.member.TermType;

public record AgreementTerm(
    TermType termType,
    String version
) {

  public TermIdDto toTermIdDto() {
    return TermIdDto.builder()
        .termType(termType)
        .version(version)
        .build();
  }
}
