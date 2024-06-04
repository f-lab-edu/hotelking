package com.hotelking.global.util;

import java.util.Random;

public class PhoneAuthCodeGenerator {

  private static final Random random = new Random();

  public static String generateAuthCode() {
    int randomNumber = 100000 + random.nextInt(900000);
    return String.valueOf(randomNumber);
  }

}
