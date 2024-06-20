package com.hotelking.domain.user;

import com.hotelking.domain.user.ServiceRequiredTerm;
import com.hotelking.domain.user.ServiceType;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRequiredTermRepository extends JpaRepository<ServiceRequiredTerm, Long> {

  Set<ServiceRequiredTerm> findDistinctByServiceType(ServiceType serviceType);
}
