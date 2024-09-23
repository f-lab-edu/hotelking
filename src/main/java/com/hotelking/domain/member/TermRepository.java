package com.hotelking.domain.member;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {

  Set<Term> findTermsByTermIdIn(Set<TermId> termTypes);

}
