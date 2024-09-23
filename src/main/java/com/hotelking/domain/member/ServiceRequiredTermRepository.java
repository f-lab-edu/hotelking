package com.hotelking.domain.member;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequiredTermRepository extends JpaRepository<ServiceRequiredTerm, Long> {

  Set<ServiceRequiredTerm> findDistinctByServiceType(ServiceType serviceType);
}
