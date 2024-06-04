package com.hotelking.domain.user;

import com.hotelking.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Term extends BaseTimeEntity {

  @EmbeddedId
  private TermId termId;

  @Column(name = "agreement_content", nullable = false)
  private String content;

  @Column(name = "agreement_mandatory", nullable = false)
  private boolean isMandatory;

  public Term(TermId termId, String content, boolean isMandatory) {
    this.termId = termId;
    this.content = content;
    this.isMandatory = isMandatory;
  }
}
