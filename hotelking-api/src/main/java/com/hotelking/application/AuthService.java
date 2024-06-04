package com.hotelking.application;

import static com.hotelking.exception.ErrorCode.USER_AUTH_PHONE_CONFIRM_DECRYPT_ERROR;
import static com.hotelking.exception.ErrorCode.USER_AUTH_PHONE_CONFIRM_NOTFOUND;

import com.hotelking.auth.PhoneAuth;
import com.hotelking.auth.PhoneAuthCode;
import com.hotelking.auth.PhoneAuthRepository;
import com.hotelking.dto.auth.PhoneAuthDto;
import com.hotelking.dto.auth.PhoneAuthToken;
import com.hotelking.dto.auth.ConfirmAuthDto;
import com.hotelking.exception.HotelkingException;
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

}
