package com.hotelking.domain.user;

import com.hotelking.domain.BaseTimeEntity;
import com.hotelking.domain.user.vo.UserName;
import com.hotelking.domain.user.vo.UserPhone;
import com.hotelking.domain.user.vo.UserStatus;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", unique = true, nullable = false)
  private String userId;

  @Column(name = "user_pwd", nullable = false)
  private String password;

  @Embedded
  private UserPhone userPhone;

  @Embedded
  private UserName userName;

  @Embedded
  private UserStatus userStatus;

  @Builder
  public User(final Long id, final String userId, final String password, final String phoneNumber) {
    if (id != null) {
      this.id = id;
    }
    validateNotNullAndNotEmpty(userId, password);
    this.userId = userId;
    this.password = password;
    this.userPhone = new UserPhone(phoneNumber);
    this.userStatus = new UserStatus();
  }

  private void validateNotNullAndNotEmpty(String userId, String password) {
    if (!StringUtils.hasLength(userId)) {
      throw new HotelkingException(ErrorCode.USER_INVALID_PARAM_ID, null);
    }

    if (!StringUtils.hasLength(password)) {
      throw new HotelkingException(ErrorCode.USER_INVALID_PARAM_PWD, null);
    }
  }

}
