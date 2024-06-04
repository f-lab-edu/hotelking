package com.hotelking.dto.request;

import com.hotelking.domain.user.vo.TermType;
import com.hotelking.dto.TermIdDto;

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
