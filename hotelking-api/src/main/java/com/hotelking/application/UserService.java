package com.hotelking.application;

import com.hotelking.domain.user.ServiceRequiredTerm;
import com.hotelking.domain.user.ServiceRequiredTerms;
import com.hotelking.domain.user.ServiceType;
import com.hotelking.domain.user.Term;
import com.hotelking.domain.user.TermId;
import com.hotelking.domain.user.User;
import com.hotelking.dto.AddUserDto;
import com.hotelking.dto.TermIdsDto;
import com.hotelking.exception.ErrorCode;
import com.hotelking.exception.HotelkingException;
import com.hotelking.infra.ServiceRequiredTermRepository;
import com.hotelking.infra.TermRepository;
import com.hotelking.infra.UserRepository;
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

}
