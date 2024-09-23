package com.hotelking.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TermId implements Serializable {

  @Enumerated(value = EnumType.STRING)
  @Column(name = "agreement_name", nullable = false, updatable = false, unique = true)
  private TermType termType;

  @Column(name = "agreement_version", nullable = false, updatable = false)
  private String version;

  @Builder
  public TermId(TermType termType, String version) {
    this.termType = termType;
    this.version = version;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof TermId termId)) {
      return false;
    }

    if (termType != termId.termType) {
      return false;
    }
    return Objects.equals(version, termId.version);
  }

  @Override
  public int hashCode() {
    int result = termType != null ? termType.hashCode() : 0;
    result = 31 * result + (version != null ? version.hashCode() : 0);
    return result;
  }
}
