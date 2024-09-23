package com.hotelking.api;

import com.hotelking.api.common.ApiResponse;
import com.hotelking.api.dto.request.AccessTokenIssueRequest;
import com.hotelking.api.dto.request.AddUserRequest;
import com.hotelking.api.dto.request.LoginRequest;
import com.hotelking.api.dto.request.PhoneAuthConfirmRequest;
import com.hotelking.api.dto.response.JwtTokenResponse;
import com.hotelking.api.dto.response.UserIdResponse;
import com.hotelking.domain.member.service.AuthService;
import com.hotelking.domain.member.service.LoginService;
import com.hotelking.domain.member.service.UserService;
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

  @PostMapping("/login/token")
  public ApiResponse<JwtTokenResponse> issueAccessToken(
      @RequestBody AccessTokenIssueRequest atIssueRequest
  ) {
    atIssueRequest.validationCheck();
    return ApiResponse.success(loginService.reIssueAccessToken(atIssueRequest.refreshToken()));
  }

  @PostMapping("/find-user-id")
  public ApiResponse<UserIdResponse> findUserId(@RequestBody PhoneAuthConfirmRequest phoneAuthConfirmRequest) {
    authService.checkTokenVerified(phoneAuthConfirmRequest.token());
    final String userId = userService.findUserId(phoneAuthConfirmRequest.toDto());
    return ApiResponse.success(new UserIdResponse(userId));
  }
}
