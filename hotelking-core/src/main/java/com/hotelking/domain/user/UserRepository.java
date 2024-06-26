package com.hotelking.domain.user;

import com.hotelking.domain.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value =
      "select count(u.id) > 0 from User u " +
          "where u.userId = :userId " +
          "and u.userStatus.isWithdrawal is false "
  )
  boolean existsUserId(String userId);

  @Override
  @Query(value =
      "select count(u.id) > 0 from User u " +
          "where u.id = :userPid " +
          "and u.userStatus.isWithdrawal is false "
  )
  boolean existsById(Long userPid);

  @Query(value = "select u from User u " +
      "where u.userId = :userId " +
      "and u.userStatus.isWithdrawal is false")
  Optional<User> findByUserId(String userId);

  @Query(value =
      "select count(u.id) > 0 from User u "
          + "where u.userPhone = :phoneNumber "
          + "and u.userStatus.isWithdrawal is false")
  boolean existsByUserPhone(String phoneNumber);

  @Query(value =
      "select u from User u "
          + "where u.userPhone = :phoneNumber "
          + "and u.userStatus.isWithdrawal is false")
  Optional<User> findUserByPhone(String phoneNumber);
}
