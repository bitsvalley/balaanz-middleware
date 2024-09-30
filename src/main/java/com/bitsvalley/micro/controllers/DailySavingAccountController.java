package com.bitsvalley.micro.controllers;

import static com.bitsvalley.micro.utils.BVMicroUtils.formatCurrency;

import com.bitsvalley.micro.domain.*;
import com.bitsvalley.micro.model.SMSContent;
import com.bitsvalley.micro.repositories.AccountTypeRepository;
import com.bitsvalley.micro.repositories.UserRepository;
import com.bitsvalley.micro.services.*;
import com.bitsvalley.micro.utils.BVMicroUtils;
import com.bitsvalley.micro.utils.TransactionStatus;
import com.bitsvalley.micro.utils.TransactionType;
import com.bitsvalley.micro.webdomain.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * @author Fru Chifen
 * 11.06.2021
 */
@Controller
@Slf4j
public class DailySavingAccountController extends SuperController {

    @Autowired
    AccountTypeRepository accountTypeRepository;

    @Autowired
    CallCenterService callCenterService;

    @Autowired
    DailySavingAccountService dailySavingAccountService;

    @Autowired
    LoanAccountService loanAccountService;

    @Autowired
    GeneralLedgerService generalLedgerService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PdfService pdfService;

    @Autowired
    BranchService branchService;

    @Autowired
    InitSystemService initSystemService;
    @Autowired
    NotificationService notificationService;

    @GetMapping(value = "/registerDailySavingAccount")
    public String registerSaving(ModelMap model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(BVMicroUtils.CUSTOMER_IN_USE);
        if (user == null) {
            return "findCustomer";
        }
        DailySavingAccount savingAccount = new DailySavingAccount();
        model.put("dailySavingAccount", savingAccount);
        List<AccountType> byOrgIdAndCategory = accountTypeRepository.findByOrgIdAndCategoryAndActiveTrue(user.getOrgId(), BVMicroUtils.SAVINGS);
        model.put("accountTypes", byOrgIdAndCategory);
        return "dailySavingAccount";
    }


    @PostMapping(value = "/registerDailySavingAccountForm")
    public String registerSavingAccount(@ModelAttribute("saving") DailySavingAccount savingAccount, ModelMap model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(BVMicroUtils.CUSTOMER_IN_USE);
        user = userRepository.findById(user.getId()).get();
        Branch branchInfo = branchService.getBranchInfo(getLoggedInUserName());//TODO Create branch repo
        savingAccount.setBranchCode(branchInfo.getCode());
        savingAccount.setCountry(branchInfo.getCountry());
        dailySavingAccountService.createSavingAccount(savingAccount, user);
        return findUserByUserName(user, model, request);
    }



    @GetMapping(value = "/registerDailySavingAccountTransaction/{id}")
    public String registerSavingAccountTransaction(@PathVariable("id") long id, ModelMap model, HttpServletRequest request) {
        DailySavingAccountTransaction savingAccountTransaction = new DailySavingAccountTransaction();
        RuntimeSetting runtimeSetting = (RuntimeSetting) request.getSession().getAttribute("runtimeSettings");
        return displaySavingBilanzNoInterest(id, model, savingAccountTransaction, runtimeSetting);
    }

    private String displaySavingBilanzNoInterest(long id, ModelMap model, DailySavingAccountTransaction dailySavingAccountTransaction, RuntimeSetting runtimeSetting) {
        Optional<DailySavingAccount> savingAccount = dailySavingAccountService.findById(id);
        DailySavingAccount aSavingAccount = savingAccount.get();
        List<DailySavingAccountTransaction> savingAccountTransactionList = aSavingAccount.getDailySavingAccountTransaction();
        Collections.reverse(savingAccountTransactionList);
        SavingBilanzList savingBilanzByUserList = dailySavingAccountService.calculateAccountBilanz(savingAccountTransactionList, false, runtimeSetting.getCountryCode());
        model.put("name", getLoggedInUserName());
        model.put("dailySavingBilanzList", savingBilanzByUserList);

        dailySavingAccountTransaction.setDailySavingAccount(aSavingAccount);
        model.put("dailySavingAccountTransaction", dailySavingAccountTransaction);
        model.put("glSearchDTO", new GLSearchDTO());

        return "dailySavingBilanzNoInterest";
    }

    @GetMapping(value = "/dailyStatementPDF/{id}")
    public void generateStatementPDF(@PathVariable("id") long id, ModelMap model, HttpServletRequest request, HttpServletResponse response) throws IOException {
        User user = (User) request.getSession().getAttribute(BVMicroUtils.CUSTOMER_IN_USE);
        RuntimeSetting runtimeSetting = (RuntimeSetting) request.getSession().getAttribute("runtimeSettings");
        response.setHeader("Content-disposition","attachment;filename="+ id+"_statement.pdf");
//            OutputStream responseOutputStream = response.getOutputStream();
            Optional<DailySavingAccount> dailySavingAccount = dailySavingAccountService.findById(Long.valueOf(id));
            SavingBilanzList savingBilanzByUserList = dailySavingAccountService.
                    calculateAccountBilanz(dailySavingAccount.get().getDailySavingAccountTransaction(),false, runtimeSetting.getCountryCode());

            Collections.reverse(savingBilanzByUserList.getSavingBilanzList());

            String htmlInput = pdfService.generatePDFDailySavingBilanzList(savingBilanzByUserList, dailySavingAccount.get(),runtimeSetting.getLogo(), initSystemService.findByOrgId(user.getOrgId()) );
            generateByteOutputStream(response, htmlInput);
    }


    private boolean getUserInUse(ModelMap model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(BVMicroUtils.CUSTOMER_IN_USE);
        if (user == null) {
            model.addAttribute("user", new User());
            return true;
        }
        try {
            user.getSavingAccount().size();
        } catch (RuntimeException exp) {
            model.addAttribute("user", new User());
            return true;
        }
        return false;
    }


    @PostMapping(value = "/registerDailySavingAccountTransactionForm")
    public String registerSavingAccountTransactionForm(ModelMap model, @ModelAttribute("dailySavingAccountTransaction") DailySavingAccountTransaction dailySavingAccountTransaction, HttpServletRequest request) {

        User user = (User) request.getSession().getAttribute(BVMicroUtils.CUSTOMER_IN_USE);
        getRepresentative(dailySavingAccountTransaction, user);
        String savingAccountId = request.getParameter("dailySavingAccountId");
        DailySavingAccount dailySavingAccount = dailySavingAccountService.findById(new Long(savingAccountId)).get();
        dailySavingAccountTransaction.setDailySavingAccount(dailySavingAccount);
        dailySavingAccountTransaction.setOrgId(user.getOrgId());
        String deposit_withdrawal = request.getParameter("deposit_withdrawal");
        String error = "";
        dailySavingAccountTransaction.setWithdrawalDeposit(1);
        RuntimeSetting runtimeSetting = (RuntimeSetting) request.getSession().getAttribute("runtimeSettings");

        if(StringUtils.isEmpty( dailySavingAccountTransaction.getModeOfPayment() ) ){
            error = "Select Method of Payment - MOP";
        }
        else if(StringUtils.isEmpty(deposit_withdrawal)){
            error = "Select Transaction Type";
        }

        if (deposit_withdrawal.equals("WITHDRAWAL")) {
            dailySavingAccountTransaction.setSavingAmount(dailySavingAccountTransaction.getSavingAmount() * -1);
            dailySavingAccountTransaction.setWithdrawalDeposit(-1);
            error = dailySavingAccountService.withdrawalAllowed(dailySavingAccountTransaction);
//            debitCredit = BVMicroUtils.DEBIT;
            //Make sure min amount is not violated at withdrawal
        }

        if (!StringUtils.isEmpty(error)) {
            model.put("billSelectionError", error);
            dailySavingAccountTransaction.setNotes(dailySavingAccountTransaction.getNotes());
            return displaySavingBilanzNoInterest(Long.parseLong(savingAccountId), model, dailySavingAccountTransaction, runtimeSetting);
        }

        if((dailySavingAccountTransaction.getSavingAmount() + dailySavingAccountTransaction.getDailySavingAccount().getAccountBalance() ) < dailySavingAccountTransaction.getDailySavingAccount().getAccountMinBalance()){
            dailySavingAccount.setDefaultedPayment(true);// Minimum balance check

//            model.put("billSelectionError", "Please make minimum payment of "+ BVMicroUtils.formatCurrency(dailySavingAccountTransaction.getDailySavingAccount().getMinimumPayment(),runtimeSetting.getCountryCode()));
//            dailySavingAccountTransaction.setNotes(dailySavingAccountTransaction.getNotes());
//            return displaySavingBilanzNoInterest(Long.parseLong(savingAccountId), model, dailySavingAccountTransaction, runtimeSetting);
        }
        if ("CASH".equals(dailySavingAccountTransaction.getModeOfPayment()) && "true".equals(runtimeSetting.getBillSelectionEnabled()) ) {
            if (!checkBillSelectionMatchesEnteredAmount(dailySavingAccountTransaction)) {
                model.put("billSelectionError", "Bills Selection does not match entered amount");
                dailySavingAccountTransaction.setNotes(dailySavingAccountTransaction.getNotes());
                return displaySavingBilanzNoInterest(Long.parseLong(savingAccountId), model, dailySavingAccountTransaction, runtimeSetting);
            }
        }

        String modeOfPayment = request.getParameter("modeOfPayment");
        dailySavingAccountTransaction.setModeOfPayment(modeOfPayment);
        Branch branchInfo = branchService.getBranchInfo(getLoggedInUserName());

        dailySavingAccountTransaction.setBranch(branchInfo.getId());
        dailySavingAccountTransaction.setBranchCode(branchInfo.getCode());
        dailySavingAccountTransaction.setBranchCountry(branchInfo.getCountry());
        dailySavingAccountTransaction.setTransactionType(TransactionType.COLLECTED.name());
        dailySavingAccountService.createDailySavingAccountTransaction(dailySavingAccountTransaction, dailySavingAccount);

        generalLedgerService.updateGLAfterCashDailySavingAccountTransaction(dailySavingAccountTransaction);
        String username = getLoggedInUserName();
        callCenterService.saveCallCenterLog(dailySavingAccountTransaction.getReference(),
                username, dailySavingAccount.getAccountNumber(),
            "Daily Saving account transaction made " + formatCurrency(
                dailySavingAccountTransaction.getSavingAmount(), runtimeSetting.getCountryCode())
                + " " + dailySavingAccountTransaction.getNotes());
        // send sms
        if (StringUtils.isNotBlank(user.getTelephone1()) && user.getTelephone1().length() == 9) {
            SMSContent smsContent = SMSContent.builder()
                .transactType(deposit_withdrawal)
                .name(user.getFirstName().concat(" ").concat(user.getLastName()))
                .accountNumber(dailySavingAccount.getAccountNumber())
                .sendTo(user.getTelephone1())
                .businessName(runtimeSetting.getBusinessName())
                .amount(formatCurrency(dailySavingAccountTransaction.getSavingAmount()))
                .balance(formatCurrency(dailySavingAccount.getAccountBalance()))
                .build();
            notificationService.sendSms(smsContent);
        } else {
            log.info("SKIPPED SMS NOTIFICATION FOR USER : {}, PHONE1 IS INVALID",
                user.getUserName());
        }
        SavingBilanzList savingBilanzByUserList = dailySavingAccountService.calculateAccountBilanz(dailySavingAccount.getDailySavingAccountTransaction(), false, runtimeSetting.getCountryCode());
        model.put("name", username );
        model.put("billSelectionInfo",
            formatCurrency(dailySavingAccountTransaction.getSavingAmount(),
                runtimeSetting.getCountryCode()) + " ---- PAYMENT HAS REGISTERED ----- ");
        model.put("dailySavingBilanzList", savingBilanzByUserList);
        request.getSession().setAttribute("dailySavingBilanzList", savingBilanzByUserList);
        Optional<User> byId = userRepository.findById(user.getId());
        request.getSession().setAttribute(BVMicroUtils.CUSTOMER_IN_USE, byId.get());
        dailySavingAccountTransaction.setDailySavingAccount(dailySavingAccount);
        resetSavingsAccountTransaction(dailySavingAccountTransaction); //reset BillSelection and amount
        dailySavingAccountTransaction.setNotes("");
        model.put("dailySavingAccountTransaction", dailySavingAccountTransaction);

        return "dailySavingBilanzNoInterest";

    }

    private void getRepresentative(DailySavingAccountTransaction savingAccountTransaction, User user) {
        if(null == savingAccountTransaction.getAccountOwner()){
            savingAccountTransaction.setAccountOwner("false");
        }
        if (StringUtils.isEmpty(savingAccountTransaction.getRepresentative())) {
            savingAccountTransaction.setRepresentative(BVMicroUtils.getFullName(user));
        }
    }

    @GetMapping(value = "/showUserDailySavingBilanz/{id}")
    public String showUserSavingBilanz(@PathVariable("id") long id, ModelMap model, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(BVMicroUtils.CUSTOMER_IN_USE);
        RuntimeSetting runtimeSetting = (RuntimeSetting) request.getSession().getAttribute("runtimeSettings");
        SavingBilanzList savingBilanzByUserList = dailySavingAccountService.getSavingBilanzByUser(user, true, runtimeSetting.getCountryCode());
        model.put("name", getLoggedInUserName());
        model.put("savingBilanzList", savingBilanzByUserList);
        return "savingBilanz";
    }

    @GetMapping(value = "/showDailySavingAccountBilanz/{accountId}")
    public String showSavingAccountBilanz(@PathVariable("accountId") long accountId, ModelMap model, HttpServletRequest request) {
        RuntimeSetting runtimeSetting = (RuntimeSetting) request.getSession().getAttribute("runtimeSettings");
        Optional<DailySavingAccount> byId = dailySavingAccountService.findById(accountId);
        List<DailySavingAccountTransaction> savingAccountTransaction = byId.get().getDailySavingAccountTransaction();
        SavingBilanzList savingBilanzByUserList = dailySavingAccountService.calculateAccountBilanz(savingAccountTransaction, true, runtimeSetting.getCountryCode());
        model.put("name", getLoggedInUserName());
        model.put("savingBilanzList", savingBilanzByUserList);
        return "savingBilanz";
    }


    private void resetSavingsAccountTransaction(DailySavingAccountTransaction sat) {
        sat.setSavingAmount(0);
        sat.setFifty(0);
        sat.setFiveHundred(0);
        sat.setFiveThousand(0);
        sat.setOneHundred(0);
        sat.setOneThousand(0);
        sat.setTenThousand(0);
        sat.setTwentyFive(0);
        sat.setTwoThousand(0);
    }

    private boolean checkBillSelectionMatchesEnteredAmount(DailySavingAccountTransaction sat) {

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

        boolean match = (sat.getSavingAmount() == selection) || (sat.getSavingAmount()*-1 == selection) ;

        if (match) {
            sat.setNotes(sat.getNotes()
                    + addBillSelection(sat));
        }
        return match;
    }

    private String addBillSelection(DailySavingAccountTransaction sat) {
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

}