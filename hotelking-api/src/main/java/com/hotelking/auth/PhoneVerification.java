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
@Table(name = "phone_verification")
public class PhoneVerification extends BaseTimeEntity {

  public static final int INTERVAL_MINUTE = 3;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  private PhoneVerifyCode verifyCode;

  @Column(name = "is_verified", nullable = false)
  private boolean isVerifed;

  @Column(name = "expired_at", nullable = false, updatable = false)
  @Temporal(value = TemporalType.TIMESTAMP)
  private LocalDateTime expiredAt;

  @Column(name = "verified_at")
  @Temporal(value = TemporalType.TIMESTAMP)
  private LocalDateTime verifiedAt;

  @Builder
  public PhoneVerification(final Long id, final PhoneVerifyCode verifyCode) {
    if (Objects.nonNull(id)) {
      this.id = id;
    }

    if (verifyCode == null) {
      throw new HotelkingException(ErrorCode.USER_AUTH_PHONE, null);
    }

    this.verifyCode = verifyCode;
    this.isVerifed = false;
    this.expiredAt = generateExpiredDate();
  }

  private LocalDateTime generateExpiredDate() {
    return LocalDateTime.now().plusMinutes(INTERVAL_MINUTE);
  }
}
