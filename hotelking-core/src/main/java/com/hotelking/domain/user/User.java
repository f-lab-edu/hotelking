package com.hotelking.domain.user;

import com.hotelking.domain.BaseTimeEntity;
import com.hotelking.domain.user.vo.UserName;
import com.hotelking.domain.user.vo.UserStatus;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import com.hotelking.util.PhoneNumberValidator;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "USERS")
public class User extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id", unique = true, nullable = false)
  private String userId;

  @Column(name = "user_pwd", nullable = false)
  private String password;

  @Column(name = "user_phone", nullable = false)
  private String userPhone;

  @Embedded
  private UserName userName;

  @Embedded
  private UserStatus userStatus;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
  private Set<UserAgreement> userAgreements = new HashSet<>();

  @Builder
  public User(final Long id, final String userId, final String password, final String phoneNumber) {
    if (id != null) {
      this.id = id;
    }
    validateNotNullAndNotEmpty(userId, password);
    if (PhoneNumberValidator.isNotValidFormat(phoneNumber)) {
      throw new HotelkingException(ErrorCode.USER_INVALID_PARAM_PHONE, null);
    }

    this.userId = userId;
    this.password = password;
    this.userPhone = phoneNumber;
    this.userStatus = new UserStatus();
  }

  private void validateNotNullAndNotEmpty(String userId, String password) {
    if (!StringUtils.hasLength(userId)) {
      throw new HotelkingException(ErrorCode.USER_INVALID_PARAM_ID, null);
    }

    if (!StringUtils.hasLength(password)) {
      throw new HotelkingException(ErrorCode.USER_INVALID_PARAM_PWD, null);
    }
  }

  public void agreeToTerms(Set<Term> terms) {
    this.userAgreements = createUserAgreements(terms);
  }

  private Set<UserAgreement> createUserAgreements(Set<Term> terms) {
    return terms.stream().map(it ->
            UserAgreement.builder()
                .userAgreementId(
                    UserAgreementId.builder()
                        .user(this)
                        .term(it)
                        .build())
                .isAgree(true)
                .build())
        .collect(Collectors.toUnmodifiableSet());
  }

  public String getPassword() {
    return password;
  }

  public Long getId() {
    return id;
  }

  public String getUserId() {
    return userId;
  }
}
