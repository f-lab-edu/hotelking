package com.hotelking.domain.user.vo;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserStatus {

  @Column(name = "user_withdraw", nullable = false)
  private boolean isWithdraw;

  @Column(name = "deleted_at", columnDefinition = "TIMESTAMP(0)", nullable = false)
  @Temporal(value = TemporalType.TIMESTAMP)
  private LocalDateTime deletedAt;

  private boolean isNotDeleted() {
    return !isWithdraw;
  }
}
