package com.bitsvalley.micro.services;

import com.bitsvalley.micro.domain.*;
import com.bitsvalley.micro.repositories.*;
import com.bitsvalley.micro.utils.AccountStatus;
import com.bitsvalley.micro.utils.BVMicroUtils;
import com.bitsvalley.micro.webdomain.SavingBilanz;
import com.bitsvalley.micro.webdomain.SavingBilanzList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class DailySavingAccountService extends SuperService {


    @Autowired
    private DailySavingAccountRepository dailySavingAccountRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private CurrentAccountRepository currentAccountRepository;

    @Autowired
    private DailySavingAccountTransactionRepository dailySavingAccountTransactionRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CallCenterRepository callCenterRepository;

    @Autowired
    private GeneralLedgerService generalLedgerService;

    @Autowired
    private InterestService interestService;

    @Autowired
    private CallCenterService callCenterService;

    @Autowired
    private LoanAccountService loanAccountService;

    @Autowired
    private BranchService branchService;

    @Autowired
    private CurrentAccountService currentAccountService;

    @Autowired
    private CurrentAccountTransactionRepository currentAccountTransactionRepository;

    private double minimumSaving;

    public DailySavingAccount findByAccountNumberAndOrgId(String accountNumber, long orgId) {
        return dailySavingAccountRepository.findByAccountNumberAndOrgId(accountNumber, orgId);
    }

    public void createSavingAccount(DailySavingAccount savingAccount, User user) {

//        ArrayList<UserRole> userRoleList = new ArrayList<UserRole>();
//        UserRole customer = userRoleService.findUserRoleByName(user.getUserRole().get(0).getName(), user.getOrgId());
//        userRoleList.add(customer);
//        ArrayList<User> customerList = userService.findAllByUserRoleIn(userRoleList, user.getOrgId());

        int countNumberOfProductsInBranch = dailySavingAccountRepository.countNumberOfProductsCreatedInBranch(user.getBranch().getCode(),user.getOrgId());
        savingAccount.setAccountNumber(BVMicroUtils.getCobacSavingsAccountNumber(savingAccount.getCountry(),
                savingAccount.getProductCode(), countNumberOfProductsInBranch, user.getCustomerNumber(), savingAccount.getBranchCode())); //TODO: Collision

        savingAccount.setAccountStatus(AccountStatus.ACTIVE);
        savingAccount.setCreatedBy(getLoggedInUserName());
        savingAccount.setCreatedDate(new Date(System.currentTimeMillis()));
        savingAccount.setLastUpdatedBy(getLoggedInUserName());
        savingAccount.setAccountLocked(false);
        savingAccount.setOrgId(user.getOrgId());
        savingAccount.setLastUpdatedDate(new Date(System.currentTimeMillis()));

        AccountType savingAccountType = accountTypeRepository.findByNumberAndOrgIdAndActiveTrue(savingAccount.getProductCode(), savingAccount.getOrgId());
        savingAccount.setAccountType(savingAccountType);

        savingAccount.setUser(user);
        dailySavingAccountRepository.save(savingAccount);
        callCenterService.callCenterDailySavingAccount(savingAccount);

        user = userRepository.findById(user.getId()).get();
        user.getDailySavingAccount().add(savingAccount);
        userService.saveUser(user);

//        //TODO: Move to callCenter service
//        callCenterService.callCenterUpdate(savingAccount);

    }


    public SavingBilanzList getSavingAccountByUser(User user, boolean calculateInterest, String currencyCode) {
        User aUser = null;
        if (null != user.getUserName()) {
            aUser = userRepository.findByUserName(user.getUserName());
        } else {
            aUser = userRepository.findById(user.getId()).get();
        }
        ArrayList<User> userList = new ArrayList<User>();
        userList.add(aUser);
        return calculateUsersInterest(userList, calculateInterest, currencyCode);
    }

    @Transactional
    public void createDailySavingAccountTransaction(DailySavingAccountTransaction dailySavingAccountTransaction) {
        //Get id of savingAccount transaction
        dailySavingAccountTransaction.setReference(BVMicroUtils.getSaltString()); //Collision
        dailySavingAccountTransaction.setCreatedBy(getLoggedInUserName());
        dailySavingAccountTransaction.setCreatedDate(LocalDateTime.now());
        dailySavingAccountTransaction.setAccountBalance(calculateAccountBalance(dailySavingAccountTransaction.getSavingAmount(), dailySavingAccountTransaction.getDailySavingAccount()));

        dailySavingAccountTransactionRepository.save(dailySavingAccountTransaction);
//        generalLedgerService.updateGLAfterSavingAccountTransaction(savingAccountTransaction);
    }


    public double calculateAccountBalance(double savingAmount, DailySavingAccount savingAccount) {

        Double balance = 0.0;
        for (DailySavingAccountTransaction transaction : savingAccount.getDailySavingAccountTransaction()) {
            balance = transaction.getSavingAmount() + balance;
        }
        return savingAmount + balance;
    }

    @Transactional
    public void createCurrentAccountTransaction(CurrentAccountTransaction currentAccountTransaction) {
        //Get id of savingAccount transaction
        currentAccountTransaction.setReference(BVMicroUtils.getSaltString()); //Collision
        currentAccountTransaction.setCreatedBy(getLoggedInUserName());
        currentAccountTransaction.setCreatedDate(LocalDateTime.now());
        currentAccountTransactionRepository.save(currentAccountTransaction);
        // generalLedgerService.updateSavingAccountTransaction(savingAccountTransaction);
    }

    @Transactional
    public void createDailySavingAccountTransaction(DailySavingAccountTransaction savingAccountTransaction, DailySavingAccount savingAccount) {
        //Get id of savingAccount transactions
        createDailySavingAccountTransaction(savingAccountTransaction);
        if (savingAccount.getDailySavingAccountTransaction() != null) {
            savingAccount.getDailySavingAccountTransaction().add(savingAccountTransaction);
        } else {
            savingAccount.setDailySavingAccountTransaction(new ArrayList<DailySavingAccountTransaction>());
            savingAccount.getDailySavingAccountTransaction().add(savingAccountTransaction);
        }
        save(savingAccount);
    }


    public Optional<DailySavingAccount> findById(long id) {
        Optional<DailySavingAccount> dailySavingAccount = dailySavingAccountRepository.findById(id);
        return dailySavingAccount;
    }

    public void save(DailySavingAccount save) {
        dailySavingAccountRepository.save(save);
    }


    public SavingBilanzList getSavingBilanzByUser(User user, boolean calculateInterest, String currencyCode) {
        User aUser = null;
        if (null != user.getUserName()) {
            aUser = userRepository.findByUserName(user.getUserName());
        } else {
            aUser = userRepository.findById(user.getId()).get();
        }
        ArrayList<User> userList = new ArrayList<User>();
        userList.add(aUser);
        return calculateUsersInterest(userList, calculateInterest, currencyCode);

    }

    public SavingBilanzList calculateAccountBilanz(
            List<DailySavingAccountTransaction> savingAccountTransactions,
            boolean calculateInterest, String currencyCode) {
        double totalSaved = 0.0;
        double currentSaved = 0.0;
        double savingAccountTransactionInterest = 0.0;

        SavingBilanzList savingBilanzsList = new SavingBilanzList();
        for (int k = 0; k < savingAccountTransactions.size(); k++) {

            final DailySavingAccountTransaction savingAccountTransaction = savingAccountTransactions.get(k);
            SavingBilanz savingBilanz = new SavingBilanz();
            savingBilanz = calculateInterest(savingAccountTransaction, calculateInterest, currencyCode);

            currentSaved = currentSaved + savingAccountTransaction.getSavingAmount();

            savingBilanz.setCurrentBalance(BVMicroUtils.formatCurrency(currentSaved, currencyCode));
            // TODO: need robust handling here as of now proceed
            // TODO: Define constants for statuses.
            String transactionType = savingAccountTransactions.get(k).getTransactionType();
            if(!"AVAILABLE".equals(transactionType)){
                savingBilanz.setTransactionType("COLLECTED");
            }else {
                savingBilanz.setTransactionType("");
            }
            savingBilanzsList.getSavingBilanzList().add(savingBilanz);
            totalSaved = totalSaved + savingAccountTransaction.getSavingAmount();
            if (calculateInterest) {
                savingAccountTransactionInterest = savingAccountTransactionInterest +
                        interestService.calculateInterestAccruedMonthCompounded(
                                savingAccountTransaction.getDailySavingAccount().getInterestRate(),
                                savingAccountTransaction.getCreatedDate(),
                                savingAccountTransaction.getSavingAmount());
                savingBilanzsList.setTotalSavingInterest(BVMicroUtils.formatCurrency(savingAccountTransactionInterest, currencyCode));
            }
        }
        savingBilanzsList.setTotalSaving(BVMicroUtils.formatCurrency(totalSaved, currencyCode));
        return savingBilanzsList;
    }


    private SavingBilanzList calculateUsersInterest(ArrayList<User> users, boolean calculateInterest, String countryCode) {
        double totalSaved = 0.0;
        double currentSaved = 0.0;
        double savingAccountTransactionInterest = 0.0;
        SavingBilanzList savingBilanzsList = new SavingBilanzList();
        for (int i = 0; i < users.size(); i++) {
            List<DailySavingAccount> savingAccounts = users.get(i).getDailySavingAccount();
            List<DailySavingAccountTransaction> savingAccountTransactions = new ArrayList<DailySavingAccountTransaction>();
            for (int j = 0; j < savingAccounts.size(); j++) {
                DailySavingAccount dailySavingAccount = savingAccounts.get(j);
//                SavingBilanz sb = new SavingBilanz();
//                sb.setRepresentative(dailySavingAccount.get);
//                savingBilanzsList.setSavingBilanzList();
                boolean defaultedPayments = checkDefaultLogic(dailySavingAccount);
                dailySavingAccount.setDefaultedPayment(defaultedPayments); //TODO:defaultLogic
                savingAccountTransactions = dailySavingAccount.getDailySavingAccountTransaction();
                double accountTotalSaved = 0.0;
                for (int k = 0; k < savingAccountTransactions.size(); k++) {
                    final DailySavingAccountTransaction savingAccountTransaction = savingAccountTransactions.get(k);

                    SavingBilanz savingBilanz = calculateInterest(savingAccountTransaction, calculateInterest, countryCode);
                    savingBilanz.setRepresentative(savingAccountTransaction.getRepresentative());
                    currentSaved = currentSaved + savingAccountTransaction.getSavingAmount();
                    savingBilanz.setCurrentBalance(BVMicroUtils.formatCurrency(currentSaved,countryCode));
                    savingBilanzsList.getSavingBilanzList().add(savingBilanz);
                    totalSaved = totalSaved + savingAccountTransaction.getSavingAmount();
                    accountTotalSaved = accountTotalSaved + savingAccountTransaction.getSavingAmount();
                    savingAccountTransactionInterest = savingAccountTransactionInterest +
                            interestService.calculateInterestAccruedMonthCompounded(
                                    savingAccountTransaction.getDailySavingAccount().getInterestRate(),
                                    savingAccountTransaction.getCreatedDate(),
                                    savingAccountTransaction.getSavingAmount());
                }
                dailySavingAccount.setAccountBalance(accountTotalSaved);
                if (!defaultedPayments) {
                    boolean minBalance = checkMinBalanceLogin(dailySavingAccount) ? true : false;
                    dailySavingAccount.setDefaultedPayment(minBalance);// Minimum balance check
                }
                dailySavingAccountRepository.save(dailySavingAccount);
            }
        }

        savingBilanzsList.setTotalSaving(BVMicroUtils.formatCurrency(totalSaved,countryCode));
        savingBilanzsList.setTotalSavingInterest(BVMicroUtils.formatCurrency(savingAccountTransactionInterest,countryCode));
//        Collections.reverse(savingBilanzsList.getSavingBilanzList());
        return savingBilanzsList;
    }

    public boolean checkMinBalanceLogin(DailySavingAccount savingAccount) {

        if (savingAccount.getAccountMinBalance() > savingAccount.getAccountBalance()) {
            callCenterService.saveCallCenterLog("", savingAccount.getUser().getUserName(), savingAccount.getAccountNumber(), BVMicroUtils.MINIMUM_BALANCE_NOT_MET_FOR_THIS_ACCOUNT+savingAccount.getAccountNumber());
            return true;
        }

        return false;
    }


    public SavingBilanz calculateInterest(DailySavingAccountTransaction savingAccountTransaction, boolean calculateInterest, String countryCode) {
        SavingBilanz savingBilanz = new SavingBilanz();
        savingBilanz.setId("" + savingAccountTransaction.getId());
        savingBilanz.setAccountType(savingAccountTransaction.getDailySavingAccount().getAccountSavingType().getName());
        savingBilanz.setAccountMinimumBalance(BVMicroUtils.formatCurrency(savingAccountTransaction.getDailySavingAccount().getAccountMinBalance(), countryCode));
        savingBilanz.setMinimumBalance(BVMicroUtils.formatCurrency(savingAccountTransaction.getDailySavingAccount().getAccountMinBalance(), countryCode));
        savingBilanz.setCreatedBy(savingAccountTransaction.getCreatedBy());
        savingBilanz.setReference(savingAccountTransaction.getReference());
        savingBilanz.setAgent(savingAccountTransaction.getCreatedBy());
        savingBilanz.setInterestRate("" + savingAccountTransaction.getDailySavingAccount().getInterestRate());
        savingBilanz.setSavingAmount(savingAccountTransaction.getSavingAmount());
        savingBilanz.setRepresentative(savingAccountTransaction.getRepresentative());
        savingBilanz.setCreatedDate(BVMicroUtils.formatDateTime(savingAccountTransaction.getCreatedDate()));
        savingBilanz.setNotes(savingAccountTransaction.getNotes());
        savingBilanz.setAccountNumber(savingAccountTransaction.getDailySavingAccount().getAccountNumber());
        savingBilanz.setNoOfDays(calculateNoOfDays(savingAccountTransaction.getCreatedDate()));
        savingBilanz.setModeOfPayment(savingAccountTransaction.getModeOfPayment());
        savingBilanz.setAccountOwner(savingAccountTransaction.getAccountOwner());
        savingBilanz.setBranch(savingAccountTransaction.getDailySavingAccount().getBranchCode());

        if (calculateInterest) {
            savingBilanz.setInterestAccrued(
                    BVMicroUtils.formatCurrency(
                            interestService.calculateInterestAccruedMonthCompounded(
                                    savingAccountTransaction.getDailySavingAccount().getInterestRate(),
                                    savingAccountTransaction.getCreatedDate(),
                                    savingAccountTransaction.getSavingAmount()),countryCode));
        }
        return savingBilanz;
    }

    private String calculateNoOfDays(LocalDateTime createdDate) {
        long noOfDays = createdDate.until(LocalDateTime.now(), ChronoUnit.DAYS);
        return "" + noOfDays;
    }


    public boolean checkDefaultLogic(DailySavingAccount savingAccount) {

//        if(savingAccount.getAccountSavingType().getName().equals("GENERAL SAVINGS")){
        List<DailySavingAccountTransaction> savingAccountTransactionList = savingAccount.getDailySavingAccountTransaction();
        LocalDateTime currentDateCal = LocalDateTime.now();
        Date input = savingAccount.getCreatedDate();
        LocalDate createdLocalDate = input.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


        long monthsBetween = ChronoUnit.MONTHS.between(
                YearMonth.from(LocalDate.of(createdLocalDate.getYear(), createdLocalDate.getMonth(), createdLocalDate.getDayOfMonth())),
                YearMonth.from(LocalDate.of(currentDateCal.getYear(), currentDateCal.getMonth(), currentDateCal.getDayOfMonth())));

        if (monthsBetween >= savingAccountTransactionList.size()) {
            CallCenter callCenter = new CallCenter();
            callCenter.setNotes(BVMicroUtils.REGULAR_MONTHLY_PAYMENT_MISSING);
            callCenter.setDate(new Date(System.currentTimeMillis()));
            callCenter.setReference(savingAccount.getUser().getFirstName() + " " + savingAccount.getUser().getLastName());
            callCenter.setAccountNumber(savingAccount.getAccountNumber());
            return true;
        }

        return false;
    }

    private String padding(int i) {
        if (i < 10)
            return "" + 0 + 1;
        return "" + i;
    }

    public String withdrawalAllowed(DailySavingAccountTransaction savingTransaction) {
        String error = "";
        error = minimumSavingRespected(savingTransaction);
        return error;
    }

    private String minimumSavingRespected(DailySavingAccountTransaction savingTransaction) {
        double futureBalance = getAccountBalance(savingTransaction.getDailySavingAccount()) + savingTransaction.getSavingAmount();
        if (savingTransaction.getDailySavingAccount().getAccountMinBalance() > futureBalance) {
            return "Account will fall below Minimum Daily Savings amount";
        }
        return null;
    }

    public double getAccountBalance(DailySavingAccount savingAccount) {
        double total = 0.0;
        List<DailySavingAccountTransaction> savingAccountTransactions = savingAccount.getDailySavingAccountTransaction();
        for (DailySavingAccountTransaction tran : savingAccountTransactions) {
            total = tran.getSavingAmount() + total;
        }
        return total;
    }

}
