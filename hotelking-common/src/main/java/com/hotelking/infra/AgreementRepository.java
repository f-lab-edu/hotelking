package com.hotelking.infra;

import com.hotelking.domain.user.Agreement;
import com.hotelking.domain.user.vo.AgreementName;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementRepository extends JpaRepository<Agreement, Long> {

  Set<Agreement> findAgreementsByNameIn(Set<AgreementName> agreementNames);
}
