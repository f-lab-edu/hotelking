package com.hotelking.domain.member;

import jakarta.persistence.Embeddable;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
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
  @JoinColumns({
      @JoinColumn(name = "agreement_name", referencedColumnName = "agreement_name"),
      @JoinColumn(name = "agreement_version", referencedColumnName = "agreement_version")
  })
  private Term term;

  @ManyToOne(fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "user_id", nullable = false, updatable = false, foreignKey = @ForeignKey(name = "fk_user_useragmt"))
  private User user;

  @Builder
  public UserAgreementId(Term term, User user) {
    this.term = term;
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
    int result = term.hashCode();
    result = 31 * result + user.hashCode();
    return result;
  }
}
