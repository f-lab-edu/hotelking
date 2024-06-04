package com.hotelking.dto;

import com.hotelking.domain.user.TermId;
import com.hotelking.domain.user.vo.TermType;
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
