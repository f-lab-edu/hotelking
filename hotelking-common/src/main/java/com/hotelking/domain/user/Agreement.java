package com.hotelking.domain.user;

import com.hotelking.domain.BaseTimeEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

  @Column(name = "aggre_name", nullable = false)
  private String name;

  @Column(name = "aggre_content", nullable = false)
  private String content;

  @Column(name = "aggre_mandatory", nullable = false)
  private boolean isMandatory;

  @OneToMany(cascade = CascadeType.REMOVE, orphanRemoval = true, mappedBy = "agreement")
  private List<AgreementVersion> versions = new ArrayList<>();

}
