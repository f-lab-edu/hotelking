package com.hotelking.domain.user;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;

@Embeddable
public class UserStatus {

  @Column(name = "user_withdraw", nullable = false)
  private boolean isWithdraw;

  @Column(name = "deleted_at", columnDefinition = "TIMESTAMP(0)", nullable = false)
  @Temporal(value = TemporalType.TIMESTAMP)
  private LocalDateTime deletedAt;

  public void delete() {
    if (isNotDeleted()) {
      isWithdraw = true;
      deletedAt = LocalDateTime.now();
    }
  }

  private boolean isNotDeleted() {
    return !isWithdraw;
  }
}
