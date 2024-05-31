package com.hotelking.dto;

import com.hotelking.domain.user.vo.AgreementName;
import com.hotelking.exception.HotelkingException;
import java.util.Set;
import lombok.Builder;

@Builder
public record AgreementNames(Set<AgreementName> agreementNames) {

  public void checkRequiredAgreementsByType(ServiceType serviceType) {
    if (!serviceType.requiredAgreementNames.equals(agreementNames)) {
      throw new HotelkingException(null, null);
    }
  }

  public Set<AgreementName> toSet() {
    return agreementNames;
  }

}