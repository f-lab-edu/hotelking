package com.hotelking.application;

import com.hotelking.auth.PhoneAuth;
import com.hotelking.auth.PhoneAuthCode;
import com.hotelking.auth.PhoneAuthCodeGenerator;
import com.hotelking.auth.PhoneAuthDto;
import com.hotelking.auth.PhoneAuthRepository;
import com.hotelking.auth.PhoneAuthToken;
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

  @Transactional
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

}
