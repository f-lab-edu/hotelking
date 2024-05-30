package com.hotelking.controller;

import com.hotelking.application.AuthService;
import com.hotelking.auth.PhoneAuthToken;
import com.hotelking.dto.request.PhoneAuthCodeRequest;
import com.hotelking.response.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping(value = "/phone-verification")
  public ApiResponse<PhoneAuthToken> verifyPhone(@RequestBody PhoneAuthCodeRequest phoneAuthCodeRequest) {
    phoneAuthCodeRequest.validationCheck();
    PhoneAuthToken phoneAuthToken = authService.issuePhoneAuth(phoneAuthCodeRequest.toDto());
    return ApiResponse.success(phoneAuthToken);
  }
}
