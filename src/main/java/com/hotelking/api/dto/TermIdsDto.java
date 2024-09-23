package com.hotelking.api.dto;

import com.hotelking.domain.member.TermId;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Builder;

@Builder
public record TermIdsDto(Set<TermIdDto> termIdDtoSet) {

  public Set<TermId> toTermIds() {
    return termIdDtoSet.stream()
        .map(TermIdDto::toTermId)
        .collect(Collectors.toSet());
  }
}
