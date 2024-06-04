package com.hotelking.domain.user.vo;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.time.LocalDateTime;

@Embeddable
public class UserStatus {

  @Column(name = "user_withdrawal")
  private boolean isWithdrawal;

  @Column(name = "deleted_at", columnDefinition = "TIMESTAMP(0)")
  @Temporal(value = TemporalType.TIMESTAMP)
  private LocalDateTime deletedAt;

  private boolean isNotDeleted() {
    return !isWithdrawal;
  }

  public UserStatus() {
    this.isWithdrawal = false;
  }
}
