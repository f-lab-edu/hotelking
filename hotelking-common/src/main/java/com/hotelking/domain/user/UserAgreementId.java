package com.hotelking.domain.user;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class UserAgreementId implements Serializable {

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "aggrement_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_agmt_useragmt"))
  private Agreement agreement;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user_useragmt"))
  private User user;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof UserAgreementId that)) {
      return false;
    }

    if (!agreement.equals(that.agreement)) {
      return false;
    }
    return user.equals(that.user);
  }

  @Override
  public int hashCode() {
    int result = agreement.hashCode();
    result = 31 * result + user.hashCode();
    return result;
  }
}
