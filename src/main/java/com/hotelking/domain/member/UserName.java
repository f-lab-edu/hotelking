package com.hotelking.domain.member;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserName {

  @Column(name = "user_first_name")
  private String firstName;

  @Column(name = "user_last_name")
  private String lastName;

  public String getFullName() {
    if (firstName == null || lastName == null) {
      return "";
    }
    return new StringBuilder()
        .append(lastName)
        .append(" ")
        .append(firstName)
        .toString();
  }
}
