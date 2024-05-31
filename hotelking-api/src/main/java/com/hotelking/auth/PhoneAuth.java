package com.hotelking.auth;

import com.hotelking.domain.BaseTimeEntity;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "phone_auth")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PhoneAuth extends BaseTimeEntity {

  public static final int INTERVAL_MINUTE = 3;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private PhoneNumber phoneNumber;

  @Embedded
  private PhoneAuthCode authCode;

  @Column(name = "is_verified", nullable = false)
  private boolean isVerified;

  @Column(name = "expired_at", nullable = false, updatable = false)
  @Temporal(value = TemporalType.TIMESTAMP)
  private LocalDateTime expiredAt;

  @Column(name = "verified_at")
  @Temporal(value = TemporalType.TIMESTAMP)
  private LocalDateTime verifiedAt;

  @Builder
  public PhoneAuth(final Long id, final PhoneAuthCode authCode, final String phoneNumber) {
    if (Objects.nonNull(id)) {
      this.id = id;
    }

    if (authCode == null) {
      throw new HotelkingException(ErrorCode.USER_AUTH_PHONE_CODE, null);
    }

    this.phoneNumber = new PhoneNumber(phoneNumber);
    this.authCode = authCode;
    this.isVerified = false;
    this.expiredAt = generateExpiredDate();
  }

  private LocalDateTime generateExpiredDate() {
    return LocalDateTime.now().plusMinutes(INTERVAL_MINUTE);
  }

  public boolean isVerified() {
    return isVerified;
  }

  private boolean isTimeOver() {
    return expiredAt.isBefore(LocalDateTime.now());
  }

  public void confirm() {
    this.isVerified = true;
    this.verifiedAt = LocalDateTime.now();
  }

  private boolean notMatches(PhoneAuthCode authCode, PhoneNumber phoneNumber) {
    return !this.authCode.isSame(authCode) || !this.phoneNumber.isSame(phoneNumber);
  }

  public void checkConfirmable(PhoneAuthCode authCode, PhoneNumber phoneNumber) {
    if (isVerified()) {
      throw new HotelkingException(ErrorCode.USER_AUTH_PHONE_CONFIRM_VERIFIED, null);
    }

    if (isTimeOver()) {
      throw new HotelkingException(ErrorCode.USER_AUTH_PHONE_CONFIRM_TIMEOVER, null);
    }

    if (notMatches(authCode, phoneNumber)) {
      throw new HotelkingException(ErrorCode.USER_AUTH_PHONE_CONFIRM_NOT_SAME, null);
    }
  }

}
