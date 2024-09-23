package com.hotelking.api.dto.request;

import java.util.Arrays;

public enum AuthServiceType {
  SIGNUP, FIND_USER_ID, FIND_USER_PWD;

  public static final AuthServiceType[] userCheckServiceTypes = {FIND_USER_ID, FIND_USER_PWD};

  public boolean isUserCheckServiceType() {
    return Arrays.asList(userCheckServiceTypes).contains(this);
  }
}
