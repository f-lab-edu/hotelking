package com.hotelking.domain.user;

import com.hotelking.domain.BaseTimeEntity;
import com.hotelking.domain.user.vo.AgreementName;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Agreement extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(value = EnumType.STRING)
  @Column(name = "agreement_name", nullable = false, unique = true)
  private AgreementName name;

  @Column(name = "agreement_content", nullable = false)
  private String content;

  @Column(name = "agreement_mandatory", nullable = false)
  private boolean isMandatory;

  @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "agreement")
  private List<AgreementVersion> versions = new ArrayList<>();

}
