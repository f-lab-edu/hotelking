package com.hotelking.dto;

import static com.hotelking.domain.user.vo.AgreementName.MARKETING;
import static com.hotelking.domain.user.vo.AgreementName.PRIVACY_POLICY;
import static com.hotelking.domain.user.vo.AgreementName.TERMS_OF_SERVICE;

import com.hotelking.domain.user.vo.AgreementName;
import java.util.Set;

public enum ServiceType {
  SIGNUP(Set.of(TERMS_OF_SERVICE, PRIVACY_POLICY), Set.of(MARKETING));

  final Set<AgreementName> requiredAgreementNames;
  final Set<AgreementName> optionalAgreementNames;

  ServiceType(
      Set<AgreementName> requiredAgreementNames,
      Set<AgreementName> optionalAgreementNames
  ) {
    this.requiredAgreementNames = requiredAgreementNames;
    this.optionalAgreementNames = optionalAgreementNames;
  }
}