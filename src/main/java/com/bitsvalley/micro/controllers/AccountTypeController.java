package com.bitsvalley.micro.controllers;

import com.bitsvalley.micro.domain.AccountType;
import com.bitsvalley.micro.domain.LedgerAccount;
import com.bitsvalley.micro.domain.User;
import com.bitsvalley.micro.repositories.AccountTypeRepository;
import com.bitsvalley.micro.repositories.LedgerAccountRepository;
import com.bitsvalley.micro.repositories.UserRepository;
import com.bitsvalley.micro.utils.BVMicroUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author Fru Chifen
 * 11.06.2021
 */
@Controller
public class AccountTypeController extends SuperController {

    @Autowired
    AccountTypeRepository accountTypeRepository;

    @Autowired
    LedgerAccountRepository ledgerAccountRepository;

    @Autowired
    UserRepository userRepository;

    @PostMapping(value = "/registerAccountTypeForm")
    public String registerAccountTypeForm(@ModelAttribute("branch") AccountType accountType,
                                          ModelMap model, HttpServletRequest request) {
        User user = userRepository.findByUserName(getLoggedInUserName());
        if (user == null) {
            accountType.setOrgId(0);
        } else {
            accountType.setOrgId(user.getOrgId());
        }
        accountType.setActive(true);
        accountTypeRepository.save(accountType);

        List<LedgerAccount> byCodeAndOrgId = ledgerAccountRepository.findByCodeAndOrgId(accountType.getName() + "_GL_" + accountType.getCode(), user.getOrgId());
        if(byCodeAndOrgId == null || byCodeAndOrgId.size() == 0) {

            //Create LedgerAccount
            LedgerAccount ledgerAccount = new LedgerAccount();
            ledgerAccount.setName(accountType.getName());
            ledgerAccount.setCategory(accountType.getCategory());
            ledgerAccount.setCode(accountType.getName() + "_GL_" + accountType.getCode());
            ledgerAccount.setDisplayName(accountType.getDisplayName());
            ledgerAccount.setStatus(BVMicroUtils.ACTIVE);
            ledgerAccount.setCreatedBy(user.getUserName());
            ledgerAccount.setCreditBalance("true");
            ledgerAccount.setCashAccountTransfer("true");
            ledgerAccount.setCashTransaction("true");
            ledgerAccount.setActive(true);

            ledgerAccount.setInterAccountTransfer("true");
            ledgerAccount.setCreatedDate(new Date());
            ledgerAccount.setOrgId(user.getOrgId());
            ledgerAccountRepository.save(ledgerAccount);
        }

        model.put("accountType", accountType);
        model.put("accountTypeInfo", accountType.getDisplayName() + " - New AccountType Created");
        model.put("accountTypes", accountTypeRepository.findByOrgId(user.getOrgId()));
        return "accountType";
    }


    @GetMapping(value = "/registerAccountType")
    public String registerAcountType(ModelMap model, HttpServletRequest request) {
        AccountType accountType = new AccountType();
        model.put("accountType", accountType);
        return "accountType";
    }

    @GetMapping(value = "/updateAccountType")
    public String updateAccountType(ModelMap model, HttpServletRequest request) {
        final Iterable<AccountType> all = accountTypeRepository.findAll();
        for (AccountType aAccountType : all) {
            if (new Integer(aAccountType.getNumber()) > 10 && new Integer(aAccountType.getNumber()) < 20) {
                if (StringUtils.equals(aAccountType.getName(), BVMicroUtils.DAILY_SAVINGS)) {
                    aAccountType.setCategory(BVMicroUtils.DAILY_SAVINGS);
                } else {
                    aAccountType.setCategory(BVMicroUtils.SAVINGS);
                }

            } else if (new Integer(aAccountType.getNumber()) > 40 && new Integer(aAccountType.getNumber()) < 50) {
                aAccountType.setCategory(BVMicroUtils.LOAN);
            }
            aAccountType.setActive(true);
            accountTypeRepository.save(aAccountType);
        }
        AccountType accountType = new AccountType();
        model.put("accountType", accountType);
        return "accountType";
    }

    @GetMapping(value = "/accountTypes")
    public String branches(ModelMap model, HttpServletRequest request) {
        User user = userRepository.findByUserName(getLoggedInUserName());
        List<AccountType> accountTypes = accountTypeRepository.findByOrgIdAndActiveTrue(user.getOrgId());
        model.put("accountTypeList", accountTypes);
        return "accountTypes";
    }

    @GetMapping(value = "/accountType/{id}")
    public String showBranchAccountTypes(@PathVariable("orgId") Long orgId, ModelMap model) {
        List<AccountType> AccountTypeOrgList = accountTypeRepository.findByOrgIdAndActiveTrue(orgId);
        model.put("accountTypeList", AccountTypeOrgList);
        return "accountTypes";
    }
}