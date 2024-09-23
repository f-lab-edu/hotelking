package com.hotelking.global.util;

import java.security.SecureRandom;

public class PhoneAuthCodeGenerator {

  private static final SecureRandom random = new SecureRandom();

  public static String generateAuthCode() {
    int randomNumber = 100000 + random.nextInt(900000);
    return String.valueOf(randomNumber);
  }

}
