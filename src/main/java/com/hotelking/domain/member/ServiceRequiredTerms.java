package com.hotelking.domain.member;

import static com.hotelking.global.exception.ErrorCode.USER_NOT_REQUIRED_TERM;

import com.hotelking.global.exception.HotelkingException;
import java.util.Set;

public record ServiceRequiredTerms(Set<ServiceRequiredTerm> serviceRequiredTerms) {

  // TermIds 를 파라미터로 받아서 필요로 하는 약관이 다 있는 지 확인하는 메서드
  public void validateRequiredTerms(Set<TermId> termIds) {
    // 필수 약관이 termIds 에 모두 포함되어 있는지 확인
    for (ServiceRequiredTerm requiredTerm : serviceRequiredTerms) {
      if (!termIds.contains(requiredTerm.getTerm().getTermId())) {
        throw new HotelkingException(USER_NOT_REQUIRED_TERM, null);
      }
    }
  }
}
