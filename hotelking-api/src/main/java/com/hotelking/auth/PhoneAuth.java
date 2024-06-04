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
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "phone_auth")
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
  private boolean isVerifed;

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
    this.isVerifed = false;
    this.expiredAt = generateExpiredDate();
  }

  private LocalDateTime generateExpiredDate() {
    return LocalDateTime.now().plusMinutes(INTERVAL_MINUTE);
  }

  public Long getId() {
    return id;
  }
}
