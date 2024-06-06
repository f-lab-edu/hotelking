package com.hotelking.controller;

import com.hotelking.application.AuthService;
import com.hotelking.application.LoginService;
import com.hotelking.application.UserService;
import com.hotelking.dto.request.AddUserRequest;
import com.hotelking.dto.request.LoginRequest;
import com.hotelking.dto.response.JwtTokenResponse;
import com.hotelking.response.ApiResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

  private final UserService userService;
  private final AuthService authService;
  private final LoginService loginService;

  public UserController(UserService userService, AuthService authService,
      LoginService loginService) {
    this.userService = userService;
    this.authService = authService;
    this.loginService = loginService;
  }

  @PostMapping("/signup")
  public ApiResponse<Void> createUser(@RequestBody AddUserRequest addUserRequest) {
    addUserRequest.validationCheck();
    authService.checkTokenVerified(addUserRequest.token());
    userService.addUser(addUserRequest.toAddUserDto(), addUserRequest.toTermIdsDto());
    return ApiResponse.success();
  }

  @PostMapping("/login")
  public ApiResponse<JwtTokenResponse> loginUser(@RequestBody LoginRequest loginRequest) {
    return ApiResponse.success(loginService.login(loginRequest.toDto()));
  }
}
