package com.hotelking.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AuthConfig {

  @Value("${secret-key}")
  private String secret;

  @Value("${salt}")
  private String salt;

  @Bean
  BytesEncryptor bytesEncryptor() {
    return new AesBytesEncryptor(secret, salt);
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return Argon2PasswordEncoder.defaultsForSpringSecurity_v5_8();
  }
}
