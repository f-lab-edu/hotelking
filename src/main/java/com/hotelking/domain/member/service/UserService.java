package com.hotelking.domain.member.service;

import static com.hotelking.global.exception.ErrorCode.USER_FIND_ID_NOT_FOUND_PHONE;

import com.hotelking.api.dto.AddUserDto;
import com.hotelking.api.dto.ConfirmAuthDto;
import com.hotelking.api.dto.TermIdsDto;
import com.hotelking.domain.member.ServiceRequiredTerm;
import com.hotelking.domain.member.ServiceRequiredTermRepository;
import com.hotelking.domain.member.ServiceRequiredTerms;
import com.hotelking.domain.member.ServiceType;
import com.hotelking.domain.member.Term;
import com.hotelking.domain.member.TermId;
import com.hotelking.domain.member.TermRepository;
import com.hotelking.domain.member.User;
import com.hotelking.domain.member.UserRepository;
import com.hotelking.global.exception.ErrorCode;
import com.hotelking.global.exception.HotelkingException;
import java.util.Set;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TermRepository termRepository;
  private final ServiceRequiredTermRepository serviceRequiredTermRepository;

  public UserService(
      UserRepository userRepository,
      PasswordEncoder passwordEncoder,
      TermRepository termRepository,
      ServiceRequiredTermRepository serviceRequiredTermRepository
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.termRepository = termRepository;
    this.serviceRequiredTermRepository = serviceRequiredTermRepository;
  }

  @Transactional
  public void addUser(AddUserDto addUserDto, TermIdsDto termIdsDto) {
    verifyNonDuplicateUserId(addUserDto);
    verifySignupTermsAgreement(termIdsDto);

    final String encryptedPwd = passwordEncoder.encode(addUserDto.password());
    final Set<Term> terms = findTermsByIds(termIdsDto.toTermIds());
    User user = addUserDto.toUser(encryptedPwd);
    user.agreeToTerms(terms);
    userRepository.save(user);
  }

  private void verifySignupTermsAgreement(TermIdsDto termIdsDto) {
    Set<ServiceRequiredTerm> findServiceTerms = findRequiredTerms();
    ServiceRequiredTerms serviceRequiredTerms = new ServiceRequiredTerms(findServiceTerms);
    serviceRequiredTerms.validateRequiredTerms(termIdsDto.toTermIds());
  }

  private Set<ServiceRequiredTerm> findRequiredTerms() {
    return serviceRequiredTermRepository.findDistinctByServiceType(ServiceType.SIGNUP);
  }

  private Set<Term> findTermsByIds(Set<TermId> termIdSet) {
    return termRepository.findTermsByTermIdIn(termIdSet);
  }

  private void verifyNonDuplicateUserId(AddUserDto addUserDto) {
    if (userRepository.existsUserId(addUserDto.userId())) {
      throw new HotelkingException(ErrorCode.USER_DUPLICATED_ID, null);
    }
  }

  @Transactional(readOnly = true)
  public String findUserId(ConfirmAuthDto confirmAuthDto) {
    final User user = findUserByPhoneNumber(confirmAuthDto.phoneNumber());
    return user.getUserId();
  }

  @Transactional(readOnly = true)
  public void checkExistUserByPhoneNumber(String phoneNumber) {
    if (userRepository.existsByUserPhone(phoneNumber)) {
      throw new HotelkingException(USER_FIND_ID_NOT_FOUND_PHONE, null);
    }
  }

  private User findUserByPhoneNumber(String phoneNumber) {
    return userRepository.findUserByPhone(phoneNumber)
        .orElseThrow(() -> new HotelkingException(USER_FIND_ID_NOT_FOUND_PHONE, null));
  }
}
