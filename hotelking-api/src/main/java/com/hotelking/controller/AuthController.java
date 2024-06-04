package com.hotelking.controller;

import com.hotelking.application.AuthService;
import com.hotelking.application.UserService;
import com.hotelking.dto.auth.PhoneAuthToken;
import com.hotelking.dto.request.PhoneAuthCodeRequest;
import com.hotelking.dto.request.PhoneAuthConfirmRequest;
import com.hotelking.response.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  private final AuthService authService;
  private final UserService userService;

  public AuthController(AuthService authService, UserService userService) {
    this.authService = authService;
    this.userService = userService;
  }

  @PostMapping( "/phone-verification")
  public ApiResponse<PhoneAuthToken> verifyPhone(@RequestBody PhoneAuthCodeRequest phoneAuthCodeRequest) {
    phoneAuthCodeRequest.validationCheck();
    if (phoneAuthCodeRequest.authServiceType().isUserCheckServiceType()) {
      userService.checkExistUserByPhoneNumber(phoneAuthCodeRequest.phoneNumber());
    }
    PhoneAuthToken phoneAuthToken = authService.issuePhoneAuth(phoneAuthCodeRequest.toDto());
    return ApiResponse.success(phoneAuthToken);
  }

  @PostMapping("/phone-confirm")
  public ApiResponse<Void> confirmPhone(@RequestBody PhoneAuthConfirmRequest phoneAuthConfirmRequest) {
    phoneAuthConfirmRequest.validationCheck();
    authService.confirmPhoneAuthCode(phoneAuthConfirmRequest.toDto());
    return ApiResponse.success();
  }
}
