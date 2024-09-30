package com.bitsvalley.micro.services;

import com.bitsvalley.micro.domain.*;
import com.bitsvalley.micro.repositories.*;
import com.bitsvalley.micro.utils.BVMicroUtils;
import com.bitsvalley.micro.utils.GeneralLedgerType;
import com.bitsvalley.micro.webdomain.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author Fru Chifen
 * 11.06.2021
 */
@Service
@Slf4j
public class GeneralLedgerService extends SuperService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private GeneralLedgerRepository generalLedgerRepository;

    @Autowired
    private AccountTypeRepository accountTypeRepository;

    @Autowired
    private GeneralLedgerService generalLedgerService;

    @Autowired
    private LedgerAccountRepository ledgerAccountRepository;

    @Autowired
    private DailySavingAccountTransactionRepository dailySavingAccountTransactionRepository;

    @Autowired
    private BranchService branchService;

    public List<GeneralLedger> findByAccountNumber(String accountNumber, long orgId) {
        return generalLedgerRepository.findByAccountNumberAndOrgId(accountNumber, orgId);
    }

    public GeneralLedgerBilanz findByReference(String reference, long orgId) {

        List<GeneralLedgerWeb> generalLedgerWebs = mapperGeneralLedger(generalLedgerRepository.findByReferenceAndOrgId(reference, orgId));
        GeneralLedgerBilanz generalLedgerBilanz = getGeneralLedgerBilanz(generalLedgerWebs);
        return generalLedgerBilanz;

    }

    private LedgerAccount updateGeneralLedger(DailySavingAccountTransaction savingAccountTransaction, String accountLedger, String creditDebit,
                                              double amount, boolean generalGL) {

        GeneralLedger generalLedger;//CREDIT INTEREST PAID
        generalLedger = savingAccountGLMapper(savingAccountTransaction);
        LedgerAccount ledgerAccount = ledgerAccountRepository.findByNameAndOrgIdAndActiveTrue(accountLedger, savingAccountTransaction.getOrgId());
        if (ledgerAccount == null) {
            ledgerAccount = ledgerAccountRepository.findByCodeAndOrgIdAndActiveTrue(accountLedger, savingAccountTransaction.getOrgId());
        }
        if (generalGL) {
            generalLedger.setLedgerAccount(ledgerAccount);
        }
        generalLedger.setType(creditDebit);
        generalLedger.setAmount(amount);
        generalLedger.setAccountNumber(savingAccountTransaction.getDailySavingAccount().getAccountNumber());
        generalLedger.setOrgId(savingAccountTransaction.getOrgId());
        extractClassCodeFromCode(generalLedger, ledgerAccount);
        generalLedger.setOrgId(savingAccountTransaction.getOrgId());
        generalLedgerRepository.save(generalLedger);
        return ledgerAccount;
    }

    private void extractClassCodeFromCode(GeneralLedger generalLedger, LedgerAccount aLedgerAccount) {
        String code = aLedgerAccount.getCode();
        String classCode = code.substring(code.length() - 4, code.length() - 3);
        generalLedger.setGlClass(Integer.parseInt(classCode));
    }

    private GeneralLedger savingAccountGLMapper(DailySavingAccountTransaction savingAccountTransaction) {
        GeneralLedger gl = new GeneralLedger();
        gl.setAccountNumber(savingAccountTransaction.getDailySavingAccount().getAccountNumber());
        gl.setAmount(savingAccountTransaction.getSavingAmount());
        Date date = BVMicroUtils.convertToDate(savingAccountTransaction.getCreatedDate());
        gl.setDate(date);
        gl.setCreatedDate(date);
        gl.setLastUpdatedDate(date);
        gl.setNotes(savingAccountTransaction.getNotes());
        gl.setReference(savingAccountTransaction.getReference());
        gl.setBranchCode(savingAccountTransaction.getBranchCode());
        gl.setLastUpdatedBy(savingAccountTransaction.getCreatedBy());
        gl.setCreatedBy(savingAccountTransaction.getCreatedBy());
        gl.setRepresentative(savingAccountTransaction.getRepresentative());
        gl.setGlClass(3); //TODO Saving which class in GL ?
        gl.setType(savingAccountTransaction.getSavingAmount() >= 0 ? "CREDIT" : "DEBIT");
        return gl;
    }

    public List<GeneralLedgerWeb> mapperGeneralLedger(Iterable<GeneralLedger> resultGeneralLedger) {
        final Iterator<GeneralLedger> iterator = resultGeneralLedger.iterator();
        List<GeneralLedgerWeb> result = new ArrayList<GeneralLedgerWeb>();
        while (iterator.hasNext()) {
            GeneralLedger next = iterator.next();
            result.add(extracted(next));
        }
        return result;
    }


    public List<GeneralLedgerWeb> mapperGeneralLedger(List<GeneralLedger> gls) {

        List<GeneralLedgerWeb> result = new ArrayList<GeneralLedgerWeb>();
        for (GeneralLedger next : gls) {
            result.add(extracted(next));
        }
        return result;
    }


    private GeneralLedgerWeb extracted(GeneralLedger next) {
        GeneralLedgerWeb generalLedgerWeb = new GeneralLedgerWeb();
        generalLedgerWeb.setId(next.getId());
        generalLedgerWeb.setCreatedDate(next.getCreatedDate());
        generalLedgerWeb.setRecordedDate(next.getDate());
        generalLedgerWeb.setAccountNumber(next.getAccountNumber());
        generalLedgerWeb.setCreatedBy(next.getCreatedBy());
        generalLedgerWeb.setGlClass(next.getGlClass());
        generalLedgerWeb.setLastUpdatedDate(next.getLastUpdatedDate());
        generalLedgerWeb.setNotes(next.getNotes());
        generalLedgerWeb.setRepresentative(next.getRepresentative());
        generalLedgerWeb.setAmount(next.getAmount());
        generalLedgerWeb.setLastUpdatedBy(next.getLastUpdatedBy());
        generalLedgerWeb.setType(next.getType());
        generalLedgerWeb.setReference(next.getReference());
        generalLedgerWeb.setLedgerAccount(next.getLedgerAccount());
        return generalLedgerWeb;

    }

    @NotNull
    private GeneralLedgerBilanz getGeneralLedgerBilanz(List<GeneralLedgerWeb> generalLedgerList) {
        double debitTotal = 0.0;
        double creditTotal = 0.0;
        double currentTotal = 0.0;
        GeneralLedgerBilanz bilanz = new GeneralLedgerBilanz();
        for (GeneralLedgerWeb current : generalLedgerList) {

            if (GeneralLedgerType.CREDIT.name().equals(current.getType())) {
                current.setAmount(current.getAmount() < 0 ? current.getAmount() * -1 : current.getAmount());
                creditTotal = creditTotal + current.getAmount();
//                currentTotal = currentTotal - current.getAmount();
            } else if (GeneralLedgerType.DEBIT.name().equals(current.getType())) {
                current.setAmount(current.getAmount() > 0 ? current.getAmount() * -1 : current.getAmount());
                debitTotal = debitTotal + current.getAmount();
//                currentTotal = currentTotal - current.getAmount();
            }
            current.setCurrentTotal(creditTotal + debitTotal);
            int p = 0;
        }

        bilanz.setTotal(creditTotal - debitTotal);
        bilanz.setDebitTotal(debitTotal);
        bilanz.setCreditTotal(creditTotal);
        bilanz.setGeneralLedgerWeb(generalLedgerList);
        return bilanz;
    }

    public GeneralLedgerBilanz findGLByType(String type, long orgId) {
        List<GeneralLedger> glByType = generalLedgerRepository.findGLByTypeAndOrgId(type, orgId);
        List<GeneralLedgerWeb> generalLedgerWebList = new ArrayList<GeneralLedgerWeb>();
        for (GeneralLedger aGeneralLedger : glByType) {
            generalLedgerWebList.add(extracted(aGeneralLedger));
        }
        return getGeneralLedgerBilanz(generalLedgerWebList);
    }

    public void updateGLAfterCashDailySavingAccountTransaction(DailySavingAccountTransaction savingAccountTransaction) {
        double amount = savingAccountTransaction.getSavingAmount();
        String creditDebit = "";

        if (savingAccountTransaction.getWithdrawalDeposit() == -1) {
            updateGeneralLedger(savingAccountTransaction, BVMicroUtils.CASH, BVMicroUtils.CREDIT, savingAccountTransaction.getSavingAmount() * -1, true);
            creditDebit = BVMicroUtils.DEBIT;
        } else {
            updateGeneralLedger(savingAccountTransaction, BVMicroUtils.CASH, BVMicroUtils.DEBIT, savingAccountTransaction.getSavingAmount() * -1, true);
            creditDebit = BVMicroUtils.CREDIT;
        }
        updateGeneralLedger(savingAccountTransaction, savingAccountTransaction.getDailySavingAccount().getAccountSavingType().getName(), creditDebit, amount, true);
        savingAccountTransaction.setNotes(savingAccountTransaction.getNotes());

    }

}
