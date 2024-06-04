package com.hotelking.infra;

import com.hotelking.domain.user.Term;
import com.hotelking.domain.user.vo.TermType;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgreementRepository extends JpaRepository<Term, Long> {

  Set<Term> findAgreementsByTermTypeIn(Set<TermType> termTypes);
}
