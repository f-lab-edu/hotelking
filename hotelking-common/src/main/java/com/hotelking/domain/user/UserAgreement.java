package com.hotelking.domain.user;

import com.hotelking.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAgreement extends BaseTimeEntity {

  @EmbeddedId
  private UserAgreementId userAgreementId;

  @Column(name = "user_agreement_agree", nullable = false)
  private boolean isAgree;

  public UserAgreement(UserAgreementId userAgreementId, boolean isAgree) {
    this.userAgreementId = userAgreementId;
    this.isAgree = isAgree;
  }
}
