package com.hotelking.domain.user;

import com.hotelking.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", unique = true, nullable = false)
  private String userId;

  @Column(name = "user_pwd", nullable = false)
  private String password;

  @Column(name = "user_phone", nullable = false)
  private String phone;

  @Column(name = "user_first_name")
  private String firstName;

  @Column(name = "user_last_name")
  private String lastName;

  @Embedded
  private UserStatus userStatus;

}
