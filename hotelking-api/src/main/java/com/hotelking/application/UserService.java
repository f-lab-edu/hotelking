package com.hotelking.application;

import static com.hotelking.dto.ServiceType.SIGNUP;

import com.hotelking.domain.user.Agreement;
import com.hotelking.domain.user.User;
import com.hotelking.domain.user.UserAgreement;
import com.hotelking.domain.user.UserAgreementId;
import com.hotelking.dto.AgreementNames;
import com.hotelking.dto.AddUserDto;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import com.hotelking.infra.AgreementRepository;
import com.hotelking.infra.UserAgreementRepository;
import com.hotelking.infra.UserRepository;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final UserAgreementRepository userAgreementRepository;
  private final AgreementRepository agreementRepository;

  public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder,
      UserAgreementRepository userAgreementRepository, AgreementRepository agreementRepository) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.userAgreementRepository = userAgreementRepository;
    this.agreementRepository = agreementRepository;
  }

  @Transactional
  public void addUser(AddUserDto addUserDto, AgreementNames agreementNames) {
    verifyNonDuplicateUserId(addUserDto);
    agreementNames.checkRequiredAgreementsByType(SIGNUP);

    String encryptedPwd = passwordEncoder.encode(addUserDto.password());
    User user = addUserDto.toUser(encryptedPwd);
    userRepository.save(user);

    Set<Agreement> agreements = findAgreementsByNames(agreementNames);
    Set<UserAgreement> userAgreements = createUserAgreements(agreements, user);
    userAgreementRepository.saveAll(userAgreements);
  }

  private Set<UserAgreement> createUserAgreements(Set<Agreement> agreements, User user) {
    return agreements.stream().map(it ->
        UserAgreement.builder()
            .userAgreementId(new UserAgreementId(it, user))
            .isAgree(true)
            .build()).collect(Collectors.toSet());
  }

  private Set<Agreement> findAgreementsByNames(AgreementNames agreementNames) {
    return agreementRepository.findAgreementsByNameIn(agreementNames.toSet());
  }

  private void verifyNonDuplicateUserId(AddUserDto addUserDto) {
    if (userRepository.existsUserId(addUserDto.userId())) {
      throw new HotelkingException(ErrorCode.USER_DUPLICATED_ID, null);
    }
  }

}
