package com.hotelking.api.dto;

import com.hotelking.domain.member.TermId;
import com.hotelking.domain.member.TermType;
import lombok.Builder;

@Builder
public record TermIdDto(
    TermType termType,
    String version
) {

  public TermId toTermId() {
    return TermId.builder()
        .termType(termType)
        .version(version)
        .build();
  }
}
