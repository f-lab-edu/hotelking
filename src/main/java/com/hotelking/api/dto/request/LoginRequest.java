package com.hotelking.api.dto.request;


import com.hotelking.api.dto.LoginRequestDto;

public record LoginRequest(String userId, String password) {

  public LoginRequestDto toDto() {
    return new LoginRequestDto(userId, password);
  }
}
