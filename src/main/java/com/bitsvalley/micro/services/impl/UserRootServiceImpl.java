package com.bitsvalley.micro.services.impl;

import com.bitsvalley.micro.domain.*;
import com.bitsvalley.micro.model.exception.UserRootException;
import com.bitsvalley.micro.model.requests.CreateUserPayload;
import com.bitsvalley.micro.repositories.UserControlRepository;
import com.bitsvalley.micro.repositories.UserRepository;
import com.bitsvalley.micro.repositories.UserRoleRepository;
import com.bitsvalley.micro.services.*;
import com.bitsvalley.micro.utils.AccountStatus;
import com.bitsvalley.micro.utils.BVMicroUtils;
import com.bitsvalley.micro.webdomain.RuntimeSetting;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@Slf4j
@RequiredArgsConstructor
public class UserRootServiceImpl implements UserRootService {

  private final UserService userService;
  private final UserRoleService userRoleService;
  private final BranchService branchService;
  private final InitSystemService initSystemService;
  private final UserControlRepository userControlRepository;
  private final UserRepository userRepository;
  private final UserRoleRepository userRoleRepository;
  private final PasswordEncoder passwordEncoder;

  @Value("${customer.default.pass}")
  private String defaultPass;

  @Override
  public void create(CreateUserPayload createUserPayload) {
    validate(createUserPayload);
    if (null != userRepository.findByUserName(createUserPayload.getUserName())) {
      throw new UserRootException("USERNAME_ALREADY_EXIST", "duplicate username");
    }

    String aUserRole = createUserPayload.getUserRole();
    User user = new User();
    user = getUserRoleFromRequest(user, aUserRole);
    SecurityContext context = SecurityContextHolder.getContext();
    Authentication authentication = context.getAuthentication();
    String loggedInUserName = StringUtils.isBlank(createUserPayload.getCreateBy())
      ? authentication.getName() : createUserPayload.getCreateBy();
    user.setCreatedBy(loggedInUserName);
    User aUser = userRepository.findByUserName(authentication.getName());
    if (aUser == null) {
      user.setOrgId(0);
    } else {
      user.setOrgId(aUser.getOrgId());
    }

    user.setUserName(createUserPayload.getUserName());
    user.setFirstName(createUserPayload.getFirstName());
    user.setLastName(createUserPayload.getLastName());
    user.setGender(createUserPayload.getGender());
    user.setProfession(createUserPayload.getProfession());
    user.setDateOfBirth(createUserPayload.getDateOfBirth());
    user.setIdentityCardNumber(createUserPayload.getIdentityCardNumber());
    user.setIdentityCardExpiry(createUserPayload.getIdentityCardExpiry());
    user.setReferral(createUserPayload.getReferral());
    user.setEmail(createUserPayload.getEmail());
    user.setAddress(createUserPayload.getAddress());
    user.setTelephone1(createUserPayload.getTelephone1());
    user.setTelephone2(createUserPayload.getTelephone2());
    user.setAccountLocked(createUserPayload.isAccountLocked());
    user.setLongitude(createUserPayload.getLongitude());
    user.setLatitude(createUserPayload.getLatitude());

    String name = authentication.getName();
    user.setCreatedBy(name);
    user.setPassword(attainPassword(createUserPayload.getPassword()));

    if (StringUtils.equals(user.getUserRole().get(0).getName(), BVMicroUtils.ROLE_CUSTOMER)) {
      ArrayList<UserRole> userRoleList = new ArrayList<>();
      UserRole customer = userRoleService.findUserRoleByName(BVMicroUtils.ROLE_CUSTOMER, user.getOrgId());
      userRoleList.add(customer);
      ArrayList<User> customerList = userService.findAllByUserRoleIn(userRoleList, aUser != null ? aUser.getOrgId() : 0);
      String generalCustomerCount = BVMicroUtils.leftJustify("0",7,String.valueOf(customerList.size() + 0000001));
      user.setCustomerNumber("10" + BVMicroUtils.getTwoDigitInt(aUser != null ? aUser.getOrgId() : 0) + generalCustomerCount);
    } else if (StringUtils.equals(user.getUserRole().get(0).getName(), BVMicroUtils.ROLE_DAILY_COLLECTION_CUSTOMER)) {
      ArrayList<UserRole> userRoleList = new ArrayList<>();
      UserRole dailyCustomer = userRoleService.findUserRoleByName(BVMicroUtils.ROLE_DAILY_COLLECTION_CUSTOMER, user.getOrgId());
      userRoleList.add(dailyCustomer);
      ArrayList<User> dailyCustomerList = userService.findAllByUserRoleIn(userRoleList, aUser != null ? aUser.getOrgId() : 0);
      String generalDailyCustomerCount = BVMicroUtils.leftJustify("0",7,String.valueOf(dailyCustomerList.size() +  0000001));
      user.setDailyCustomerNumber("11" + BVMicroUtils.getTwoDigitInt(aUser != null ? aUser.getOrgId() : 0) + generalDailyCustomerCount);
      user.setCustomerNumber("11" + generalDailyCustomerCount);
    }
    if (user.getId() > 0) {
      Optional<User> byId = userRepository.findById(user.getId());
      List<DailySavingAccount> savingAccount = byId.get().getDailySavingAccount();
      user.setDailySavingAccount(savingAccount);
      userService.saveUser(user);
    } else {
      Branch branch = branchService.getBranchInfo(loggedInUserName);
      user.setBranch(branch);
      user.setAccountStatus(AccountStatus.PENDING_APPROVAL);
      UserControl userControl = new UserControl();
      userControlRepository.save(userControl);
      user.setUserControl(userControl);
      userService.createUser(user);
      RuntimeSetting runtimeSetting = initSystemService.findByOrgId(aUser.getOrgId());
    }
  }

  @Override
  public List<User> getAgents(long orgId, String username) {
    UserRole roleAgent = userRoleRepository.findByName("ROLE_AGENT");
    if(null == roleAgent) throw new UserRootException("ROLE_NOT_FOUND", "role not present for agent");

    return userRepository.findByOrgIdAndCreatedByAndUserRole(orgId, username, roleAgent)
        .stream()
        .map(this::trimUser)
        .collect(Collectors.toList());
  }
  private User trimUser(User user){
    user.setPassword(null);
    user.setUserControl(null);
    user.setBranch(null);
    user.setDailySavingAccount(null);
    user.setBeneficiary(null);
    return user;
  }

  private User getUserRoleFromRequest(User user, String aUserRoleInput) {
    UserRole aUserRole = userRoleService.findUserRoleByName(aUserRoleInput, user.getOrgId());
    if (aUserRole == null) {
      aUserRole = new UserRole();
      aUserRole.setName(aUserRoleInput);
      userRoleService.saveUserRole(aUserRole, user.getOrgId());
      aUserRole = userRoleService.findUserRoleByName(aUserRoleInput, user.getOrgId());
    }
    ArrayList<UserRole> roles = new ArrayList<>();
    roles.add(aUserRole);
    user.setUserRole(roles);
    return user;
  }

  private void validate(CreateUserPayload createUserPayload){
    String userName = createUserPayload.getUserName();
    if(StringUtils.isBlank(userName)){
      throw new UserRootException("username cannot be empty");
    }
  }
  private String attainPassword(String password){
    if (StringUtils.isNotBlank(password)){
      return passwordEncoder.encode(password);
    }else {
      return passwordEncoder.encode(defaultPass);
    }
  }
}
