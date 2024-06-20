package com.hotelking.domain.user;

import com.hotelking.domain.user.Term;
import com.hotelking.domain.user.TermId;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermRepository extends JpaRepository<Term, Long> {

  Set<Term> findTermsByTermIdIn(Set<TermId> termTypes);

}
