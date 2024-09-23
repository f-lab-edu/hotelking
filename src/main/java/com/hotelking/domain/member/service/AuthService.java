package com.hotelking.domain.member.service;


import static com.hotelking.global.exception.ErrorCode.USER_AUTH_PHONE_CONFIRM_DECRYPT_ERROR;
import static com.hotelking.global.exception.ErrorCode.USER_AUTH_PHONE_CONFIRM_NOTFOUND;
import static com.hotelking.global.exception.ErrorCode.USER_NOT_VERIFIED_PHONE;

import com.hotelking.api.dto.ConfirmAuthDto;
import com.hotelking.api.dto.PhoneAuthDto;
import com.hotelking.api.dto.PhoneAuthToken;
import com.hotelking.domain.member.PhoneAuth;
import com.hotelking.domain.member.PhoneAuthCode;
import com.hotelking.domain.member.PhoneAuthRepository;
import com.hotelking.global.exception.HotelkingException;
import com.hotelking.global.util.PhoneAuthCodeGenerator;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

  private final PhoneAuthRepository phoneAuthRepository;
  private final BytesEncryptor bytesEncryptor;

  public AuthService(PhoneAuthRepository phoneAuthRepository, BytesEncryptor bytesEncryptor) {
    this.phoneAuthRepository = phoneAuthRepository;
    this.bytesEncryptor = bytesEncryptor;
  }

  public PhoneAuthToken issuePhoneAuth(PhoneAuthDto phoneAuthDto) {
    PhoneAuthCode phoneAuthCode = new PhoneAuthCode(PhoneAuthCodeGenerator.generateAuthCode());
    PhoneAuth phoneAuth = phoneAuthDto.toPhoneAuth(phoneAuthCode);
    PhoneAuth phoneAuthSaved = phoneAuthRepository.save(phoneAuth);
    final String token = encryptedPhoneAuthId(phoneAuthSaved);
    return PhoneAuthToken.from(token);
  }

  private String encryptedPhoneAuthId(PhoneAuth phoneAuthSaved) {
    final Long id = phoneAuthSaved.getId();
    byte[] encryptedId = bytesEncryptor.encrypt(id.toString().getBytes(StandardCharsets.UTF_8));
    return Base64.getEncoder().encodeToString(encryptedId);
  }

  @Transactional
  public void confirmPhoneAuthCode(ConfirmAuthDto confirmAuthDto) {
    final Long phoneAuthId = decryptToken(confirmAuthDto.token());
    final PhoneAuth phoneAuth = findPhoneAuth(phoneAuthId);
    phoneAuth.checkConfirmable(confirmAuthDto.toAuthCode(), confirmAuthDto.toPhoneNumber());
    phoneAuth.confirm();
  }

  private PhoneAuth findPhoneAuth(Long phoneAuthId) {
    return phoneAuthRepository.findById(phoneAuthId)
        .orElseThrow(() -> new HotelkingException(USER_AUTH_PHONE_CONFIRM_NOTFOUND, null));
  }

  public Long decryptToken(String encryptedIdStr) {
    try {
      byte[] encryptedId = Base64.getDecoder().decode(encryptedIdStr);
      byte[] decryptedIdBytes = bytesEncryptor.decrypt(encryptedId);
      String decryptedIdStr = new String(decryptedIdBytes, StandardCharsets.UTF_8);
      return Long.parseLong(decryptedIdStr);
    } catch (RuntimeException e) {
      throw new HotelkingException(USER_AUTH_PHONE_CONFIRM_DECRYPT_ERROR, null);
    }
  }

  public void checkTokenVerified(String token) {
    final long phoneAuthId = decryptToken(token);
    final PhoneAuth phoneAuth = findPhoneAuth(phoneAuthId);
    if (!phoneAuth.isVerified()) {
      throw new HotelkingException(USER_NOT_VERIFIED_PHONE, null);
    }
  }
}
