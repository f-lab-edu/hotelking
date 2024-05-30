package com.hotelking.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.encrypt.AesBytesEncryptor;
import org.springframework.security.crypto.encrypt.BytesEncryptor;

@Configuration
public class AuthConfig {

  @Value("secret-key")
  private String secret;

  @Bean
  public BytesEncryptor bytesEncryptor() {
    return new AesBytesEncryptor(secret, "12362819");
  }
}
