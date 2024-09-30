package com.bitsvalley.micro.services;

import com.bitsvalley.micro.domain.*;
import com.bitsvalley.micro.model.response.AgentsMetaData;
import com.bitsvalley.micro.model.response.UserVO;
import com.bitsvalley.micro.repositories.*;
import com.bitsvalley.micro.utils.AccountStatus;
import com.bitsvalley.micro.utils.BVMicroUtils;
import com.bitsvalley.micro.webdomain.CustomersDTO;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.bitsvalley.micro.utils.TransactionType.COLLECTED;

/**
 * @author Fru Chifen
 * 11.06.2021
 */
@Service
public class UserService extends SuperService{


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private GeneralLedgerService generalLedgerService;

    @Autowired
    LedgerAccountRepository ledgerAccountRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    DailySavingsAccountService dailySavingsAccountService;
    @Autowired
    DailySavingAccountTransactionRepository dailySavingAccountTransactionRepository;

    @Transactional(readOnly = true)
    public Optional<User> getUserById(long id) {
        return userRepository.findById(id);
    }
    @Transactional(readOnly = true)
    public Optional<User> getUserByUserName(String username) {
        return Optional.ofNullable(userRepository.findByUserName(username));
    }
    public User findByUserNameAndOrgId(String userName,long orgId) {
        return userRepository.findByUserNameAndOrgId(userName, orgId);
    }

    public User createUser(User user) {

        if((user.getUserRole() != null) && StringUtils.equals(user.getUserRole().get(0).getName(),BVMicroUtils.ROLE_CASHIER) ){
            UserRole userRoleByName = userRoleService.findUserRoleByName(BVMicroUtils.ROLE_MAIN_SEARCH_USERS, user.getOrgId());
            user.getUserRole().add(userRoleByName);
            userRoleByName = userRoleService.findUserRoleByName(BVMicroUtils.ROLE_GL_ACCOUNT_EXPENSE_ENTRY, user.getOrgId());
            user.getUserRole().add(userRoleByName);

            userRoleByName = userRoleService.findUserRoleByName(BVMicroUtils.ROLE_MAIN_GL_ACCOUNTS, user.getOrgId());
            user.getUserRole().add(userRoleByName);

            userRoleByName = userRoleService.findUserRoleByName(BVMicroUtils.ROLE_CREATE_GL_ACCOUNT, user.getOrgId());
            user.getUserRole().add(userRoleByName);
        }

        Date now = new Date();
        user.setCreated(now);
        user.setAccountExpiredDate(LocalDateTime.now().plusMonths(6));
        user.setAccountBlockedDate(LocalDateTime.now().plusMonths(6));
        user.setLastUpdated(now);
        user.setAccountStatus(AccountStatus.PENDING_APPROVAL);
        user.setAccountExpired(false);
        user.setAccountLocked(false);
        insureAccountExists(user.getOrgId());
        String password = user.getPassword();
        String encode = passwordEncoder.encode(password);
        user.setPassword(encode);
        User save = userRepository.save(user);

           return save;

    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    private void insureAccountExists(long orgId){ // init system

        List<AccountType> all = accountTypeRepository.findByOrgIdAndActiveTrue(orgId);
//        Iterable<UserRole> all = userRoleService.findAll();
        if (!all.iterator().hasNext()) {

// ----------------------------------------------------------------------------------------------

            List<AccountType> typeList = new ArrayList<AccountType>();
            AccountType generalSavings = new AccountType();
            generalSavings.setNumber("11");
            generalSavings.setOrgId(orgId);
            generalSavings.setName(BVMicroUtils.GENERAL_SAVINGS);
            generalSavings.setCategory(BVMicroUtils.SAVINGS);
            generalSavings.setDisplayName(BVMicroUtils.GENERAL_SAVINGS);
            generalSavings.setActive(true);
            typeList.add(generalSavings);

            AccountType autoSavingAccountType = new AccountType();
            autoSavingAccountType.setName(BVMicroUtils.RETIREMENT_SAVINGS);
            autoSavingAccountType.setDisplayName(BVMicroUtils.RETIREMENT_SAVINGS);
            autoSavingAccountType.setCategory(BVMicroUtils.SAVINGS);
            autoSavingAccountType.setNumber("12");
            autoSavingAccountType.setOrgId(orgId);
            autoSavingAccountType.setActive(true);
            typeList.add(autoSavingAccountType);

            AccountType vacationSavingAccountType = new AccountType();
            vacationSavingAccountType.setName(BVMicroUtils.DAILY_SAVINGS);
            vacationSavingAccountType.setDisplayName(BVMicroUtils.DAILY_SAVINGS);
            vacationSavingAccountType.setCategory(BVMicroUtils.DAILY_SAVING_ACCOUNT);
            vacationSavingAccountType.setNumber("13");
            vacationSavingAccountType.setOrgId(orgId);
            vacationSavingAccountType.setActive(true);
            typeList.add(vacationSavingAccountType);

            AccountType constructionSavingAccountType = new AccountType();
            constructionSavingAccountType.setName(BVMicroUtils.MEDICAL_SAVINGS);
            constructionSavingAccountType.setDisplayName(BVMicroUtils.MEDICAL_SAVINGS);
            constructionSavingAccountType.setCategory(BVMicroUtils.SAVINGS);
            constructionSavingAccountType.setNumber("14");
            constructionSavingAccountType.setOrgId(orgId);
            constructionSavingAccountType.setActive(true);
            typeList.add(constructionSavingAccountType);

            AccountType familySavingAccountType = new AccountType();
            familySavingAccountType.setName(BVMicroUtils.SOCIAL_SAVINGS);
            familySavingAccountType.setDisplayName(BVMicroUtils.SOCIAL_SAVINGS);
            familySavingAccountType.setCategory(BVMicroUtils.SAVINGS);
            familySavingAccountType.setNumber("15");
            familySavingAccountType.setOrgId(orgId);
            familySavingAccountType.setActive(true);
            typeList.add(familySavingAccountType);

            AccountType otherSavingAccountType = new AccountType();
            otherSavingAccountType.setName(BVMicroUtils.BUSINESS_SAVINGS);
            otherSavingAccountType.setDisplayName(BVMicroUtils.BUSINESS_SAVINGS);
            otherSavingAccountType.setCategory(BVMicroUtils.SAVINGS);
            otherSavingAccountType.setNumber("16");
            otherSavingAccountType.setOrgId(orgId);
            otherSavingAccountType.setActive(true);
            typeList.add(otherSavingAccountType);

            AccountType yearlySavingAccountType = new AccountType();
            yearlySavingAccountType.setName(BVMicroUtils.CHILDREN_SAVINGS);
            yearlySavingAccountType.setDisplayName(BVMicroUtils.CHILDREN_SAVINGS);
            yearlySavingAccountType.setCategory(BVMicroUtils.SAVINGS);
            yearlySavingAccountType.setNumber("17");
            yearlySavingAccountType.setOrgId(orgId);
            yearlySavingAccountType.setActive(true);
            typeList.add(yearlySavingAccountType);

            AccountType monthlSavingAccountType = new AccountType();
            monthlSavingAccountType.setName(BVMicroUtils.REAL_ESTATE_SAVINGS);
            monthlSavingAccountType.setDisplayName(BVMicroUtils.REAL_ESTATE_SAVINGS);
            monthlSavingAccountType.setCategory(BVMicroUtils.SAVINGS);
            monthlSavingAccountType.setNumber("18");
            monthlSavingAccountType.setOrgId(orgId);
            monthlSavingAccountType.setActive(true);
            typeList.add(monthlSavingAccountType);

            AccountType dailySavingAccountType = new AccountType();
            dailySavingAccountType.setName(BVMicroUtils.EDUCATION_SAVINGS);
            dailySavingAccountType.setDisplayName(BVMicroUtils.EDUCATION_SAVINGS);
            dailySavingAccountType.setCategory(BVMicroUtils.SAVINGS);
            dailySavingAccountType.setNumber("19");
            dailySavingAccountType.setOrgId(orgId);
            dailySavingAccountType.setActive(true);
            typeList.add(dailySavingAccountType);

            AccountType shortTermLoanType = new AccountType();
            shortTermLoanType.setName(BVMicroUtils.SHORT_TERM_LOAN);
            shortTermLoanType.setDisplayName(BVMicroUtils.SHORT_TERM_LOAN);
            shortTermLoanType.setCategory("LOAN");
            shortTermLoanType.setNumber("41");
            shortTermLoanType.setOrgId(orgId);
            shortTermLoanType.setActive(true);
            typeList.add(shortTermLoanType);

            AccountType consumptionType = new AccountType();
            consumptionType.setName(BVMicroUtils.CONSUMPTION_LOAN);
            consumptionType.setDisplayName(BVMicroUtils.CONSUMPTION_LOAN);
            consumptionType.setNumber("42");
            consumptionType.setCategory("LOAN");
            consumptionType.setOrgId(orgId);
            consumptionType.setActive(true);
            typeList.add(consumptionType);

            AccountType agricultureLoanType = new AccountType();
            agricultureLoanType.setName(BVMicroUtils.AGRICULTURE_LOAN);
            agricultureLoanType.setDisplayName(BVMicroUtils.AGRICULTURE_LOAN);
            agricultureLoanType.setNumber("43");
            agricultureLoanType.setCategory("LOAN");
            agricultureLoanType.setOrgId(orgId);
            agricultureLoanType.setActive(true);
            typeList.add(agricultureLoanType);

            AccountType businessLoanType = new AccountType();
            businessLoanType.setName(BVMicroUtils.BUSINESS_INVESTMENT_LOAN);
            businessLoanType.setDisplayName(BVMicroUtils.BUSINESS_INVESTMENT_LOAN);
            businessLoanType.setNumber("44");
            businessLoanType.setCategory("LOAN");
            businessLoanType.setOrgId(orgId);
            businessLoanType.setActive(true);
            typeList.add(businessLoanType);

            AccountType schoolFeesType = new AccountType();
            schoolFeesType.setName(BVMicroUtils.SCHOOL_FEES_LOAN);
            schoolFeesType.setDisplayName(BVMicroUtils.SCHOOL_FEES_LOAN);
            schoolFeesType.setNumber("45");
            schoolFeesType.setCategory("LOAN");
            schoolFeesType.setOrgId(orgId);
            generalSavings.setActive(true);
            typeList.add(schoolFeesType);

            AccountType realEstateType = new AccountType();
            realEstateType.setName(BVMicroUtils.REAL_ESTATE_LOAN);
            realEstateType.setDisplayName(BVMicroUtils.REAL_ESTATE_LOAN);
            realEstateType.setNumber("46");
            realEstateType.setCategory("LOAN");
            realEstateType.setOrgId(orgId);
            realEstateType.setActive(true);
            typeList.add(realEstateType);

            AccountType overdraftType = new AccountType();
            overdraftType.setName(BVMicroUtils.OVERDRAFT_LOAN);
            overdraftType.setDisplayName(BVMicroUtils.OVERDRAFT_LOAN);
            overdraftType.setNumber("47");
            overdraftType.setCategory("LOAN");
            overdraftType.setOrgId(orgId);
            overdraftType.setActive(true);
            typeList.add(overdraftType);

            AccountType njangiType = new AccountType();
            njangiType.setName(BVMicroUtils.NJANGI_FINANCING);
            njangiType.setDisplayName(BVMicroUtils.NJANGI_FINANCING);
            njangiType.setNumber("48");
            njangiType.setCategory("LOAN");
            njangiType.setOrgId(orgId);
            njangiType.setActive(true);
            typeList.add(njangiType);


            AccountType currentType = new AccountType();
            currentType.setName(BVMicroUtils.CURRENT);
            currentType.setDisplayName(BVMicroUtils.CURRENT);
            currentType.setNumber("20");
            currentType.setCategory("CURRENT");
            currentType.setOrgId(orgId);
            currentType.setActive(true);
            typeList.add(currentType);

            Iterable<AccountType> savingAccountTypeListIterable = typeList;
            accountTypeRepository.saveAll(savingAccountTypeListIterable);
        }
    }

    public ArrayList<User> findAllByUserRoleIn(ArrayList<UserRole> userRole, long orgId) {
        return userRepository.findAllByUserRoleInAndOrgId(userRole, orgId);
    }

    public ArrayList<User> findAllByUserNotRoleIn(ArrayList<UserRole> userRole, long orgId) {
        return userRepository.findDistintAllByUserRoleNotInAndOrgId(userRole, orgId);
    }

    public CustomersDTO extractCustomers(User user) {
        CustomersDTO customersDTO = new CustomersDTO();
        ArrayList<UserRole> customers = new ArrayList<UserRole>();
        UserRole roleByName = userRoleRepository.findByName(BVMicroUtils.ROLE_CUSTOMER);
        customers.add(roleByName);

        ArrayList<User> allActiveCustomers = null;
        ArrayList<User> allInActiveCustomers = null;
        ArrayList<User> byOrgId = null;

        if (isContainsRole(user.getUserRole(),BVMicroUtils.ROLE_GENERAL_MANAGER) ) {
            allActiveCustomers = userRepository.findAllByUserRoleInAndOrgIdAndAccountStatus(customers, user.getOrgId(),AccountStatus.ACTIVE);
            allInActiveCustomers = userRepository.findAllByUserRoleInAndOrgIdAndAccountStatus(customers, user.getOrgId(),AccountStatus.IN_ACTIVE);
            allInActiveCustomers.addAll(userRepository.findAllByUserRoleInAndOrgIdAndAccountStatus(customers, user.getOrgId(),AccountStatus.PENDING_APPROVAL));
            byOrgId = userRepository.findByOrgId(user.getOrgId());
        } else {

            allActiveCustomers = userRepository.findAllByUserRoleInAndOrgIdAndAccountStatusAndBranch(customers, user.getOrgId(),AccountStatus.ACTIVE,user.getBranch());
            allInActiveCustomers = userRepository.findAllByUserRoleInAndOrgIdAndAccountStatusAndBranch(customers, user.getOrgId(),AccountStatus.IN_ACTIVE,user.getBranch());
            allInActiveCustomers.addAll(userRepository.findAllByUserRoleInAndOrgIdAndAccountStatusAndBranch(customers, user.getOrgId(),AccountStatus.PENDING_APPROVAL,user.getBranch()));

            byOrgId = userRepository.findByOrgIdAndBranch(user.getOrgId(), user.getBranch());
        }

        byOrgId.removeAll(allActiveCustomers);
        byOrgId.removeAll(allInActiveCustomers);

        customersDTO.setAllActiveCustomers(allActiveCustomers);
        customersDTO.setAllInActiveCustomers(allInActiveCustomers);
        customersDTO.setUserList(byOrgId);

        return customersDTO;
    }

    public AgentsMetaData extractAgents(User user) {
        if (isContainsRole(user.getUserRole(), BVMicroUtils.ROLE_GENERAL_MANAGER)) {
            List<UserVO> activeUserVOS = getActiveAgents(user.getOrgId());
            List<UserVO> inActiveUserVOS = getInActiveAgents(user.getOrgId());
            return AgentsMetaData.builder()
                .activeAgents(activeUserVOS)
                .inActiveAgents(inActiveUserVOS)
                .build();
        } else {
            List<UserVO> activeUserVOS = getActiveAgents(user.getOrgId(), user.getBranch());
            List<UserVO> inActiveUserVOS = getInActiveAgents(user.getOrgId(), user.getBranch());
            return AgentsMetaData.builder()
                .activeAgents(activeUserVOS)
                .inActiveAgents(inActiveUserVOS)
                .build();
        }
    }
    public List<UserVO> getActiveAgents(long orgId){
        ArrayList<UserRole> agentRole = getAgentRole();
        ArrayList<User> allActiveCustomers = userRepository
            .findAllByUserRoleInAndOrgIdAndAccountStatus(agentRole, orgId, AccountStatus.ACTIVE);
      return buildAgents(allActiveCustomers);
    }
    public List<UserVO> getActiveAgents(long orgId, Branch branch){
        ArrayList<UserRole> agentRole = getAgentRole();
        ArrayList<User> allActiveCustomers = userRepository
            .findAllByUserRoleInAndOrgIdAndAccountStatusAndBranch(agentRole, orgId,
                AccountStatus.ACTIVE, branch);
      return buildAgents(allActiveCustomers);
    }

    @NotNull
    private List<UserVO> buildAgents(ArrayList<User> allActiveCustomers) {
        return allActiveCustomers
            .stream()
            .filter(Objects::nonNull)
            .map(activeCustomer -> UserVO.builder()
                .id(activeCustomer.getId())
                .orgId(activeCustomer.getOrgId())
                .userName(activeCustomer.getUserName())
                .firstName(activeCustomer.getFirstName())
                .lastName(activeCustomer.getLastName())
                .gender(activeCustomer.getGender())
                .referral(activeCustomer.getReferral())
                .email(activeCustomer.getEmail())
                .created(activeCustomer.getCreated())
                .createdBy(activeCustomer.getCreatedBy())
                .accountStatus(activeCustomer.getAccountStatus())
                .unsignedAmount(
                    getUnsignedAmount(activeCustomer.getUserName())
                )
                .collectionLimit(checkUserControl(activeCustomer).getNotSignedCollectionLimit())
                .build())
            .collect(Collectors.toList());
    }

    public List<UserVO> getInActiveAgents(long orgId){
        ArrayList<UserRole> agentRole = getAgentRole();
        ArrayList<User> allInActiveCustomers = userRepository
            .findAllByUserRoleInAndOrgIdAndAccountStatus(agentRole, orgId, AccountStatus.IN_ACTIVE);
      return buildAgents(allInActiveCustomers);
    }public List<UserVO> getInActiveAgents(long orgId, Branch branch){
        ArrayList<UserRole> agentRole = getAgentRole();
        ArrayList<User> allInActiveCustomers = userRepository
            .findAllByUserRoleInAndOrgIdAndAccountStatusAndBranch(agentRole, orgId, AccountStatus.IN_ACTIVE, branch);
    return buildAgents(allInActiveCustomers);
    }

    @NotNull
    private ArrayList<UserRole> getAgentRole() {
        ArrayList<UserRole> agentRole = new ArrayList<>();
        UserRole roleByName = userRoleRepository.findByName(BVMicroUtils.ROLE_AGENT);
        agentRole.add(roleByName);
        return agentRole;
    }

    private UserControl checkUserControl(User user){
        UserControl userControl;
        if(null == user.getUserControl()) {
            userControl = new UserControl();
            user.setUserControl(userControl);
            userRepository.save(user);
        }else {
            userControl = user.getUserControl();
        }
        return userControl;
    }
    public double getUnsignedAmount(String userName){
        Double agentsUnsignedAmount = dailySavingAccountTransactionRepository
          .findAgentsUnsignedAmount(userName, COLLECTED.name());
        return null == agentsUnsignedAmount ? Double.valueOf("0.0") : agentsUnsignedAmount;
    }
}
