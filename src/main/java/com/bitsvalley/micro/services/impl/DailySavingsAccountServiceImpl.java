package com.bitsvalley.micro.services.impl;

import com.bitsvalley.micro.domain.*;
import com.bitsvalley.micro.model.SMSContent;
import com.bitsvalley.micro.model.exception.DailyAccountException;
import com.bitsvalley.micro.model.exception.UserRootException;
import com.bitsvalley.micro.model.requests.DailySavingAccountRequest;
import com.bitsvalley.micro.model.requests.DailySavingRequest;
import com.bitsvalley.micro.model.requests.RemittanceMailRequest;
import com.bitsvalley.micro.model.response.DailySavingAccountDetailsResponse;
import com.bitsvalley.micro.model.response.DailySavingAccountResponse;
import com.bitsvalley.micro.model.response.DailySavingAccountTransactions;
import com.bitsvalley.micro.model.response.DailyStats;
import com.bitsvalley.micro.repositories.DailySavingAccountRepository;
import com.bitsvalley.micro.repositories.DailySavingAccountTransactionRepository;
import com.bitsvalley.micro.repositories.RuntimePropertiesRepository;
import com.bitsvalley.micro.services.*;
import com.bitsvalley.micro.utils.BVMicroUtils;
import com.bitsvalley.micro.utils.TransactionType;
import com.bitsvalley.micro.webdomain.RuntimeSetting;
import com.bitsvalley.micro.webdomain.SavingBilanz;
import com.bitsvalley.micro.webdomain.SavingBilanzList;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.text.DecimalFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

import static com.bitsvalley.micro.utils.BVMicroUtils.formatCurrency;
import static com.bitsvalley.micro.utils.BVMicroUtils.getLoggedInUserName;
import static com.bitsvalley.micro.utils.BVMicroUtils.parseDoubleWithTwoDecimal;


@Service
@Slf4j
@RequiredArgsConstructor
public class DailySavingsAccountServiceImpl implements DailySavingsAccountService {

    public static final String PHONE_CODE = "237";
    private final DailySavingAccountService dailySavingAccountService;
    private final InterestService interestService;
    private final GeneralLedgerService generalLedgerService;
    private final BranchService branchService;
    private final UserService userService;
    private final InitSystemService initSystemService;
    private final RuntimePropertiesRepository runtimePropertiesRepository;
    private final DailySavingAccountRepository dailySavingAccountRepository;
    private final DailySavingAccountTransactionRepository dailySavingAccountTransactionRepository;

    @Override
    public DailySavingAccountResponse registerTransaction(DailySavingAccountRequest request) {
        doesAgentReachedSignedAmountLimit(request);
        DailySavingAccountTransaction dailySavingAccountTransaction = new DailySavingAccountTransaction();

        if ("CASH".equals(request.getModeOfPayment())
                && (request.isBillSectionEnabled())
                && !checkBillSelectionMatchesEnteredAmount(request)) {
            throw new DailyAccountException("billSelectionError : Bills Selection does not match entered amount");
        }
        request.setNotes(request.getNotes() + addBillSelection(request));
        mapDenominations(request, dailySavingAccountTransaction);
        dailySavingAccountTransaction.setSavingAmount(request.getSavingAmount());
        long customerId = request.getCustomerId();
        Optional<User> userById = userService.getUserById(customerId);
        User customer = userById.orElseThrow(() -> new UsernameNotFoundException("User not found"));
        mapRepresentative(dailySavingAccountTransaction, customer);

        DailySavingAccount dailySavingAccount = dailySavingAccountService
                .findById(Long.parseLong(request.getDailySavingAccountId())).orElseThrow(
                        () -> new DailyAccountException("daily saving account not found")
                );
        dailySavingAccountTransaction.setDailySavingAccount(dailySavingAccount);
        String depositOrWithdrawal = request.getTransactionType();
        dailySavingAccountTransaction.setWithdrawalDeposit(1);

        if ("WITHDRAWAL".equals(depositOrWithdrawal)) {
            dailySavingAccountTransaction.setSavingAmount(dailySavingAccountTransaction.getSavingAmount() * -1);
            dailySavingAccountTransaction.setWithdrawalDeposit(-1);
            String error = dailySavingAccountService.withdrawalAllowed(dailySavingAccountTransaction);
            if (StringUtils.isNotEmpty(error)) {
                throw new DailyAccountException("error : " + error);
            }
        }
        if ((dailySavingAccountTransaction.getSavingAmount() +
                dailySavingAccountTransaction.getDailySavingAccount().getAccountBalance())
                < dailySavingAccountTransaction.getDailySavingAccount().getAccountMinBalance()) {
            dailySavingAccount.setDefaultedPayment(true);// Minimum balance check
        }
        User user = userService.getUserById(request.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Branch branchInfo = branchService.getBranchInfo(user.getUserName());

        dailySavingAccountTransaction.setBranch(branchInfo.getId());
        dailySavingAccountTransaction.setBranchCode(branchInfo.getCode());
        dailySavingAccountTransaction.setBranchCountry(branchInfo.getCountry());
        dailySavingAccountTransaction.setOrgId(user.getOrgId());
        dailySavingAccountTransaction.setModeOfPayment(request.getModeOfPayment());
        dailySavingAccountTransaction.setTransactionType(TransactionType.COLLECTED.name());
        dailySavingAccountTransaction.setLatitude(request.getLatitude());
        dailySavingAccountTransaction.setLongitude(request.getLongitude());
        log.info("Saving daily account transaction");
        dailySavingAccountService.createDailySavingAccountTransaction(dailySavingAccountTransaction, dailySavingAccount);
        RuntimeProperties countryCode = runtimePropertiesRepository.findByPropertyNameAndOrgId("countryCode", user.getOrgId());
        calculateInterest(dailySavingAccount, false, countryCode.getPropertyValue());
        generalLedgerService.updateGLAfterCashDailySavingAccountTransaction(dailySavingAccountTransaction);
        String username = user.getUserName();

        SavingBilanzList savingBilanzByUserList = dailySavingAccountService
                .calculateAccountBilanz(dailySavingAccount.getDailySavingAccountTransaction(),
                        false, countryCode.getPropertyValue());
        int i = !savingBilanzByUserList.getSavingBilanzList().isEmpty()
                ? savingBilanzByUserList.getSavingBilanzList().size() - 1 : 0;
        List<SavingBilanz> list = Collections.singletonList(savingBilanzByUserList.getSavingBilanzList().get(i));
        savingBilanzByUserList.setSavingBilanzList(list);
        log.info("Transaction completed");
        // call the sms
        if (customer.getUserControl().isSendDailyCollectSMS()) {
            log.info("username --- ", customer.getUserName());
            log.info("phone --- ", customer.getTelephone1());
            log.info("SKIPPED SMS NOTIFICATION FOR USER : {}, SMS NOT ACTIVATED", customer.getUserName());
            if (StringUtils.isNotBlank(customer.getTelephone1()) && customer.getTelephone1().length() == 9) {
                SMSContent smsContent = getSmsContent(request, depositOrWithdrawal,
                        dailySavingAccount, PHONE_CODE + customer.getTelephone1(),
                        customer.getFirstName().concat(" ").concat(customer.getLastName()), customer.getOrgId());
//                notificationService.sendSms(smsContent);
            }
            else {
                log.info("SKIPPED SMS NOTIFICATION- FOR USER : {}, PHONE IS INVALIDD ", customer.getTelephone1());
            }
        } else {
            log.info("SKIPPED SMS NOTIFICATION FOR USER : {}, {}, SMS NOT ACTIVATED", customer.getUserName(), customer.getUserControl().isSendDailyCollectSMS());
        }
        return DailySavingAccountResponse.builder()
                .balance("")
                .savingBilanzList(savingBilanzByUserList)
                .orgId(String.valueOf(user.getOrgId()))
                .customerId(request.getCustomerId())
                .build();
    }

    private SMSContent getSmsContent(DailySavingAccountRequest request,
                                     String depositOrWithdrawal, DailySavingAccount dailySavingAccount, String phone,
                                     String name, long orgId) {
        RuntimeProperties businessName = runtimePropertiesRepository.findByPropertyNameAndOrgId(
                "Business Name", orgId);
        return SMSContent.builder()
                .transactType(depositOrWithdrawal)
                .name(name)
                .businessName(businessName.getPropertyValue())
                .amount(formatCurrency(request.getSavingAmount()))
                .accountNumber(dailySavingAccount.getAccountNumber().substring(
                        dailySavingAccount.getAccountNumber().length() - 4
                ))
                .balance(formatCurrency(dailySavingAccount.getAccountBalance()))
                .sendTo(phone)
                .build();
    }

    private void doesAgentReachedSignedAmountLimit(DailySavingAccountRequest request) {
        User agent = userService.getUserById(request.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException("Agent not found"));
        Double unsignedAmount = dailySavingAccountTransactionRepository
                .findAgentsUnsignedAmount(agent.getUserName(), TransactionType.COLLECTED.name());
        double notSignedCollectionLimit = agent.getUserControl().getNotSignedCollectionLimit();
        if ((unsignedAmount + request.getSavingAmount()) > notSignedCollectionLimit) {
            throw new DailyAccountException("Reached maximum amount limit to " + request.getTransactionType(),
                    "AGENT_UNSIGNED_LIMIT_MAXED");
        }
    }

    @Override
    public void registerDailyAccount(DailySavingRequest dailySavingAccountRequest) {
        User userById = userService.getUserById(dailySavingAccountRequest.getCustomerId())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Branch branchInfo = branchService.getBranchInfo(userById.getUserName());
        DailySavingAccount savingAccount = new DailySavingAccount();
        savingAccount.setIntervalOfSaving("01 DAY");
        savingAccount.setBranchCode(branchInfo.getCode());
        savingAccount.setCountry(branchInfo.getCountry());
        savingAccount.setIntervalOfSaving("01 DAY");
        savingAccount.setProductCode("13");
        savingAccount.setInterestRate(Float.parseFloat(dailySavingAccountRequest.getInterest()));
        savingAccount.setNotes(dailySavingAccountRequest.getNotes());
        savingAccount.setMinimumPayment(Integer.parseInt(dailySavingAccountRequest.getMinimumDeposit()));
        savingAccount.setAccountMinBalance(Integer.parseInt(dailySavingAccountRequest.getAmountOnHold()));
        dailySavingAccountService.createSavingAccount(savingAccount, userById);
        log.info("Daily account created");
    }

    @Override
    public List<DailySavingAccountDetailsResponse> fetchDetails(String id) {
        List<DailySavingAccount> dailySavingAccounts = dailySavingAccountRepository.findByUserId((id));
        log.info("accounts for id : {} are : {}", id, dailySavingAccounts.size());
        return dailySavingAccounts
                .stream()
                .map(dailySavingAccount -> DailySavingAccountDetailsResponse.builder()
                        .id(String.valueOf(dailySavingAccount.getId()))
                        .accountBalance(formatCurrency(dailySavingAccount.getAccountBalance()))
                        .accountMinimumBalance(formatCurrency(dailySavingAccount.getAccountMinBalance()))
                        .accountNumber(dailySavingAccount.getAccountNumber())
                        .active(String.valueOf(dailySavingAccount.isActive()))
                        .branchCode(dailySavingAccount.getBranchCode())
                        .country(dailySavingAccount.getCountry())
                        .createdBy(dailySavingAccount.getCreatedBy())
                        .createdDate(dailySavingAccount.getCreatedDate().toString())
                        .interestRate(String.valueOf(dailySavingAccount.getInterestRate()))
                        .lastUpdatedDate(dailySavingAccount.getLastUpdatedDate().toString())
                        .minimumPayment(String.valueOf(dailySavingAccount.getMinimumPayment()))
                        .orgId(String.valueOf(dailySavingAccount.getOrgId()))
                        .productCode(dailySavingAccount.getProductCode())
                        .userId(String.valueOf(dailySavingAccount.getUser().getId()))
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<DailySavingAccountTransactions> fetchTransactions(String accountId, int page, int size) {
        if (page < 0) page = 0;
        if (size > 100) size = 100;
        if (size < 1) size = 1;
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdDate"));
        List<DailySavingAccountTransaction> transactions = dailySavingAccountTransactionRepository
                .findByDailySavingAccountId(Long.parseLong(accountId), pageable);
        log.info("Transactions fetched : {}", transactions.size());
        return transactions.stream()
                .map(transaction -> DailySavingAccountTransactions.builder()
                        .id(String.valueOf(transaction.getId()))
                        .accountBalance(formatCurrency(transaction.getAccountBalance()))
                        .accountOwner(transaction.getAccountOwner())
                        .branch(transaction.getBranchCode())
                        .createdDate(transaction.getCreatedDate().toString())
                        .reference(transaction.getReference())
                        .savingAmount(formatCurrency(transaction.getSavingAmount()))
                        .dailySavingAccountId(transaction.getDailySavingAccount().getAccountNumber())
                        .orgId(String.valueOf(transaction.getOrgId()))
                        .transactionType(transaction.getWithdrawalDeposit() > 0 ? "C" : "D")
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Map<String, List<DailyStats>> dailyStats(String accountId) {
        // Get the system's default time zone
        ZoneId zoneId = ZoneId.systemDefault();

        // Get the current date and time
        LocalDate today = LocalDate.now(zoneId);

        // Get yesterday's date
        LocalDate yesterday = today.minusDays(0);

        // Start of yesterday (00:00:00)
        LocalDateTime startOfYesterday = yesterday.atStartOfDay();
        ZonedDateTime startOfYesterdayZoned = startOfYesterday.atZone(zoneId);

        // End of yesterday (23:59:59.999999999)
        LocalDateTime endOfYesterday = yesterday.atTime(LocalTime.MAX);
        ZonedDateTime endOfYesterdayZoned = endOfYesterday.atZone(zoneId);

        // Define the desired date-time format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        // Format the start and end of yesterday
        String formattedStartOfYesterday = startOfYesterdayZoned.format(formatter);
        String formattedEndOfYesterday = endOfYesterdayZoned.format(formatter);
        User user = userService.getUserById(Long.parseLong(accountId))
                .orElseThrow(() -> new UserRootException("User not found"));
        double unsignedAmount = userService.getUnsignedAmount(user.getUserName());
        List<Object[]> agentDailyStats = dailySavingAccountTransactionRepository.getAgentDailyStats(accountId, formattedStartOfYesterday, formattedEndOfYesterday);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime week = now.minusDays(7);
        String thisDay = now.format(formatter);
        String weekEarlier = week.format(formatter);
        List<Object[]> agentWeeklyStats = dailySavingAccountTransactionRepository.getAgentDailyStats(accountId, weekEarlier, thisDay);
        LocalDateTime month = now.minusMonths(1);
        String monthEarlier = month.format(formatter);
        List<Object[]> agentMonthlyStats = dailySavingAccountTransactionRepository.getAgentDailyStats(accountId, monthEarlier, thisDay);
        List<DailyStats> weeklyStats = getStatsList(agentWeeklyStats, unsignedAmount);
        List<DailyStats> monthlyStats = getStatsList(agentMonthlyStats, unsignedAmount);
        List<DailyStats> dailyStats = getStatsList(agentDailyStats, unsignedAmount);
        Map<String, List<DailyStats>> stats = new LinkedHashMap<>();
        stats.put("DAILY", dailyStats);
        stats.put("WEEKLY", weeklyStats);
        stats.put("MONTHLY", monthlyStats);
        return stats;
    }

    @NotNull
    private List<DailyStats> getStatsList(List<Object[]> agentDailyStats, double unsignedAmount) {
        return agentDailyStats.stream()
                .filter(arr -> arr.length == 2)
                .map(arr -> DailyStats.builder()
                        .withdrawDeposit((Integer) arr[0])
                        .savingAmount(Double.parseDouble(
                                new DecimalFormat("#.##").format(arr[1]))
                        )
                        .unsignedAmount(unsignedAmount)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<DailySavingAccountTransactions> agentlatestTransactions(long accountId) {
        Pageable pageable = PageRequest.of(0, 5);
        List<DailySavingAccountTransaction> transactions = dailySavingAccountTransactionRepository.findLatestTransactions(accountId);
        return transactions.stream()
                .map(transaction -> DailySavingAccountTransactions.builder()
                        .id(String.valueOf(transaction.getId()))
                        .accountBalance(formatCurrency(transaction.getAccountBalance()))
                        .accountOwner(transaction.getAccountOwner())
                        .branch(transaction.getBranchCode())
                        .createdDate(BVMicroUtils.formatDateTime(transaction.getCreatedDate()))
                        .reference(transaction.getReference())
                        .savingAmount(formatCurrency(transaction.getSavingAmount()))
                        .dailySavingAccountId(transaction.getDailySavingAccount().getAccountNumber())
                        .orgId(String.valueOf(transaction.getOrgId()))
                        .transactionType(transaction.getWithdrawalDeposit() > 0 ? "C" : "D")
                        .createdBy(transaction.getCreatedBy())
                        .transActionStatus(transaction.getTransactionType())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<DailySavingAccountTransactions> agentTransactions(long accountId, String transactionType) {
        List<DailySavingAccountTransaction> transactions = dailySavingAccountTransactionRepository
                .findAgentsSelectiveTransactions(accountId, transactionType);
        return transactions.stream()
                .map(transaction -> DailySavingAccountTransactions.builder()
                        .id(String.valueOf(transaction.getId()))
                        .accountBalance(formatCurrency(transaction.getAccountBalance()))
                        .accountOwner(transaction.getAccountOwner())
                        .branch(transaction.getBranchCode())
                        .createdDate(BVMicroUtils.formatDateTime(transaction.getCreatedDate()))
                        .reference(transaction.getReference())
                        .savingAmount(formatCurrency(transaction.getSavingAmount()))
                        .dailySavingAccountId(transaction.getDailySavingAccount().getAccountNumber())
                        .orgId(String.valueOf(transaction.getOrgId()))
                        .transactionType(transaction.getWithdrawalDeposit() > 0 ? "C" : "D")
                        .createdBy(transaction.getCreatedBy())
                        .transActionStatus(transaction.getTransactionType())
                        .build())
                .collect(Collectors.toList());
    }

    private void calculateInterest(DailySavingAccount dailySavingAccount,
                                   boolean calculateInterest, String countryCode) {
        double totalSaved = 0.0;
        double currentSaved = 0.0;
        double savingAccountTransactionInterest = 0.0;
        boolean defaultedPayments = dailySavingAccountService.checkDefaultLogic(dailySavingAccount);
        dailySavingAccount.setDefaultedPayment(defaultedPayments);
        List<DailySavingAccountTransaction> savingAccountTransactions = dailySavingAccount.getDailySavingAccountTransaction();
        double accountTotalSaved = 0.0;
        for (final DailySavingAccountTransaction savingAccountTransaction : savingAccountTransactions) {
            SavingBilanz savingBilanz = dailySavingAccountService.calculateInterest(savingAccountTransaction, calculateInterest, countryCode);
            savingBilanz.setRepresentative(savingAccountTransaction.getRepresentative());
            currentSaved = currentSaved + savingAccountTransaction.getSavingAmount();
            savingBilanz.setCurrentBalance(formatCurrency(currentSaved, countryCode));
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
            boolean minBalance = dailySavingAccountService.checkMinBalanceLogin(dailySavingAccount);
            dailySavingAccount.setDefaultedPayment(minBalance);// Minimum balance check
        }
        dailySavingAccountRepository.save(dailySavingAccount);
    }

    private void mapDenominations(DailySavingAccountRequest request,
                                  DailySavingAccountTransaction dailySavingAccountTransaction) {
        dailySavingAccountTransaction.setTenThousand(request.getTenThousand());
        dailySavingAccountTransaction.setFiveThousand(request.getFiveThousand());
        dailySavingAccountTransaction.setTwoThousand(request.getTwoThousand());
        dailySavingAccountTransaction.setOneThousand(request.getOneThousand());
        dailySavingAccountTransaction.setFiveHundred(request.getFiveHundred());
        dailySavingAccountTransaction.setOneHundred(request.getOneHundred());
        dailySavingAccountTransaction.setFifty(request.getFifty());
        dailySavingAccountTransaction.setTwentyFive(request.getTwentyFive());
        dailySavingAccountTransaction.setTen(request.getTen());
        dailySavingAccountTransaction.setFive(request.getFive());
        dailySavingAccountTransaction.setOne(request.getOne());
        dailySavingAccountTransaction.setNotes(request.getNotes());
    }

    private boolean checkBillSelectionMatchesEnteredAmount(DailySavingAccountRequest sat) {

        double selection = (sat.getTenThousand() * 10000) +
                (sat.getFiveThousand() * 5000) +
                (sat.getTwoThousand() * 2000) +
                (sat.getOneThousand() * 1000) +
                (sat.getFiveHundred() * 500) +
                (sat.getOneHundred() * 100) +
                (sat.getFifty() * 50) +
                (sat.getTwentyFive() * 25) +
                (sat.getTen() * 10) +
                (sat.getFive() * 5) +
                (sat.getOne() * 1);

        boolean match = (sat.getSavingAmount() == selection) || (sat.getSavingAmount() * -1 == selection);

        if (match) {
            sat.setNotes(sat.getNotes()
                    + addBillSelection(sat));
        }
        return match;
    }

    private String addBillSelection(DailySavingAccountRequest sat) {
        String billSelection = " BS \n";
        billSelection = billSelection + concatBillSelection(" 10 000x", sat.getTenThousand()) + "\n";
        billSelection = billSelection + concatBillSelection(" 5 000x", sat.getFiveThousand()) + "\n";
        billSelection = billSelection + concatBillSelection(" 2 000x", sat.getTwoThousand()) + "\n";
        billSelection = billSelection + concatBillSelection(" 1 000x", sat.getOneThousand()) + "\n";
        billSelection = billSelection + concatBillSelection(" 500x", sat.getFiveHundred()) + "\n";
        billSelection = billSelection + concatBillSelection(" 100x", sat.getOneHundred()) + "\n";
        billSelection = billSelection + concatBillSelection(" 50x", sat.getFifty());
        billSelection = billSelection + concatBillSelection(" 25x", sat.getTwentyFive());
        billSelection = billSelection + concatBillSelection(" 10x", sat.getTen()) + "\n";
        billSelection = billSelection + concatBillSelection(" 5x", sat.getFive()) + "\n";
        billSelection = billSelection + concatBillSelection(" 1x", sat.getOne());
        return billSelection;
    }

    private String concatBillSelection(String s, int qty) {
        if (qty == 0) {
            return "";
        }
        s = s + qty;
        return s;
    }

    private DailySavingAccountTransaction mapRepresentative(DailySavingAccountTransaction dailySavingAccountTransaction,
                                                            User user) {
        if (null == dailySavingAccountTransaction.getAccountOwner()) {
            dailySavingAccountTransaction.setAccountOwner("false");
        }
        if (StringUtils.isEmpty(dailySavingAccountTransaction.getRepresentative())) {
            dailySavingAccountTransaction.setRepresentative(BVMicroUtils.getFullName(user));
        }
        return dailySavingAccountTransaction;
    }
}
