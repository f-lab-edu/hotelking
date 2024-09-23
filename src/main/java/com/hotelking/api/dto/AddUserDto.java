package com.hotelking.api.dto;

import com.hotelking.domain.member.User;
import lombok.Builder;

@Builder
public record AddUserDto(String userId, String password, String phoneNumber) {

  public User toUser(final String encryptedPwd) {
    return User.builder()
        .userId(userId)
        .password(encryptedPwd)
        .phoneNumber(phoneNumber)
        .build();
  }
}