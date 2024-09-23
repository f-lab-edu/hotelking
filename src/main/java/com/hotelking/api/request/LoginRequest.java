package com.hotelking.api.request;


import com.hotelking.api.dto.LoginRequestDto;

public record LoginRequest(String userId, String password) {

  public LoginRequestDto toDto() {
    return new LoginRequestDto(userId, password);
  }
}
