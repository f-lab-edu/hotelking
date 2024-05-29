package com.hotelking.domain.user;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserAgreementId implements Serializable {

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "aggrement_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_agmt_useragmt"))
  private Agreement agreement;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user_useragmt"))
  private User user;

  @Builder
  public UserAgreementId(Agreement agreement, User user) {
    this.agreement = agreement;
    this.user = user;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserAgreementId that)) {
      return false;
    }
    return Objects.equals(user, that.user);
  }

  @Override
  public int hashCode() {
    int result = agreement.hashCode();
    result = 31 * result + user.hashCode();
    return result;
  }
}
