package com.hotelking.infra;

import com.hotelking.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value =
      "select count(u.id) > 0 from User u " +
          "where u.userId = :userId " +
          "and u.userStatus.isWithdrawal is false "
  )
  boolean existsUserId(String userId);
}
