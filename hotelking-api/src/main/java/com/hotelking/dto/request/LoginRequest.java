package com.hotelking.dto.request;

import com.hotelking.dto.LoginRequestDto;

public record LoginRequest(String userId, String password) {

  public LoginRequestDto toDto() {
    return new LoginRequestDto(userId, password);
  }
}
