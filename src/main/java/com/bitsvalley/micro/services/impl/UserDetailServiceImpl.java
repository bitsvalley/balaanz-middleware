package com.bitsvalley.micro.services.impl;

import com.bitsvalley.micro.domain.User;
import com.bitsvalley.micro.model.exception.UserRootException;
import com.bitsvalley.micro.model.response.UserDetails;
import com.bitsvalley.micro.repositories.UserRepository;
import com.bitsvalley.micro.services.UserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.bitsvalley.micro.utils.BVMicroUtils.DEFAULT_UNSIGNED_AMOUNT_LIMIT;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailService {

  private final UserRepository userRepository;

  @Override
  public UserDetails getUserDetails(String username) {
    User byUserNameAndOrgId = userRepository.findByUserName(username);

    if(null == byUserNameAndOrgId){
       throw new UserRootException("USER_NOT_FOUND", "User not found");
    }

    return UserDetails.builder()
      .id(byUserNameAndOrgId.getId())
      .userName(byUserNameAndOrgId.getUserName())
      .customerNumber(byUserNameAndOrgId.getCustomerNumber())
      .dailyCustomerNumber(byUserNameAndOrgId.getDailyCustomerNumber())
      .firstName(byUserNameAndOrgId.getFirstName())
      .lastName(byUserNameAndOrgId.getLastName())
      .orgId(byUserNameAndOrgId.getOrgId())
      .unsignedAmountLimit(byUserNameAndOrgId.getUserControl() == null
        ? DEFAULT_UNSIGNED_AMOUNT_LIMIT : byUserNameAndOrgId.getUserControl().getNotSignedCollectionLimit())
      .build();
  }
}
