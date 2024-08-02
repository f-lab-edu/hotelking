package com.hotelking.config;

import com.hotelking.auth.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

  private final LoginInterceptor loginInterceptor;

  public WebConfig(LoginInterceptor loginInterceptor) {
    this.loginInterceptor = loginInterceptor;
  }

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(loginInterceptor)
        .excludePathPatterns(
            // login
            "/login",
            "/login/token",
            "/signup",

            // user
            "/find-user-id",

            // reservation
            "/order",

            // phone auth
            "/phone-verification",
            "/phone-confirm"
        );
  }
}
