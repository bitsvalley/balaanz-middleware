package com.bitsvalley.micro.services;

import com.bitsvalley.micro.domain.RuntimeProperties;
import com.bitsvalley.micro.domain.UserRole;
import com.bitsvalley.micro.repositories.LedgerAccountRepository;
import com.bitsvalley.micro.repositories.RuntimePropertiesRepository;
import com.bitsvalley.micro.repositories.UserRoleRepository;
import com.bitsvalley.micro.utils.BVMicroUtils;
import com.bitsvalley.micro.webdomain.RuntimeSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class InitSystemService {

    @Autowired
    private RuntimePropertiesRepository runtimePropertiesRepository;

    @Autowired
    private LedgerAccountRepository ledgerAccountRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private InitSystemService initSystemService;

    public void initUserRoles() {
        Iterable<UserRole> all = userRoleRepository.findAll();
        if (all == null || !all.iterator().hasNext()) {
            System.out.println("-----          UPDATING USER_ROLES    -----------------");
            System.out.println("-----                                 -----------------");

            List<UserRole> userRoles = new ArrayList<UserRole>();
            userRoles.add(new UserRole("ROLE_MANAGER", 0));
            userRoles.add(new UserRole("ROLE_MAIN_USERS", 0));
            userRoles.add(new UserRole("ROLE_MAIN_SEARCH_USERS", 0));

            userRoles.add(new UserRole("ROLE_MAIN_TRANSFERS", 0));
            userRoles.add(new UserRole("ROLE_MAIN_LOANS", 0));
            userRoles.add(new UserRole("ROLE_MAIN_SETTINGS", 0));

            userRoles.add(new UserRole("ROLE_MAIN_BRANCHES", 0));
            userRoles.add(new UserRole("ROLE_MAIN_GENERAL_LEDGER", 0));
            userRoles.add(new UserRole("ROLE_MAIN_INVOICE", 0));
            userRoles.add(new UserRole("ROLE_MAIN_SHARES", 0));

            userRoles.add(new UserRole("ROLE_MAIN_TRIAL_BALANCE", 0));
            userRoles.add(new UserRole("ROLE_CASHIER", 0));
            userRoles.add(new UserRole("ROLE_LOAN_OFFICER", 0));

            userRoles.add(new UserRole("ROLE_CUSTOMER_SERVICE_OFFICER", 0));
            userRoles.add(new UserRole("ROLE_WORKER", 0));
            userRoles.add(new UserRole("ROLE_VIEW_ALL_ACCOUNTS", 0));

            userRoles.add(new UserRole("ROLE_CREATE_CUSTOMER_ACCOUNT", 0));
            userRoles.add(new UserRole("ROLE_CREATE_USER_ACCOUNT", 0));
            userRoles.add(new UserRole("ROLE_EDIT_CUSTOMER_ACCOUNT", 0));

            userRoles.add(new UserRole("ROLE_EDIT_USER_ACCOUNT", 0));
            userRoles.add(new UserRole("ROLE_VIEW_CUSTOMER_ACCOUNT_BALANCES", 0));
            userRoles.add(new UserRole("ROLE_PRINT_CUSTOMER_ACCOUNT_BALANCES", 0));

            userRoles.add(new UserRole("ROLE_CUSTOMER_ACCOUNT_DEPOSIT_CASH", 0));
            userRoles.add(new UserRole("ROLE_CUSTOMER_ACCOUNT_PAYOUT_CASH", 0));
            userRoles.add(new UserRole("ROLE_CUSTOMER_ACCOUNT_TYPE_TRANSFERS", 0));

            userRoles.add(new UserRole("ROLE_CUSTOMER_TRANSACTIONS_VIEW", 0));
            userRoles.add(new UserRole("ROLE_CUSTOMER_TRANSACTIONS_PRINT", 0));
            userRoles.add(new UserRole("ROLE_LOAN_APPLY", 0));

            userRoles.add(new UserRole("ROLE_LOAN_APPROVE", 0));
            userRoles.add(new UserRole("ROLE_LOAN_AMORTIZE", 0));
            userRoles.add(new UserRole("ROLE_LOAN_DELIQUENCY_RATE", 0));

            userRoles.add(new UserRole("ROLE_LOAN_GRANTED", 0));
            userRoles.add(new UserRole("ROLE_SHARE_APPLY", 0));
            userRoles.add(new UserRole("ROLE_SHARE_APPROVE", 0));

            userRoles.add(new UserRole("ROLE_SHARE_CURRENT_ACCOUNT_PAYOUT", 0));
            userRoles.add(new UserRole("ROLE_GENERAL_MANAGER", 0));
            userRoles.add(new UserRole("ROLE_ALL_BRANCH_GL", 0));

            userRoles.add(new UserRole("ROLE_GL_VIEW", 0));
            userRoles.add(new UserRole("ROLE_GL_PRINT", 0));
            userRoles.add(new UserRole("ROLE_GL_1_1_TRANSFER", 0));

            userRoles.add(new UserRole("ROLE_GL_1_MANY_TRANSFER", 0));
            userRoles.add(new UserRole("ROLE_GL_ACCOUNT_TRANSFER_TO_CUSTOMER_ACCOUNT", 0));
            userRoles.add(new UserRole("ROLE_GL_ACCOUNT_TRANSFER_FROM_CUSTOMER_ACCOUNT", 0));

            userRoles.add(new UserRole("ROLE_PRINT_CASH_SELECTION_SITUATION", 0));
            userRoles.add(new UserRole("ROLE_GL_ACCOUNT_EXPENSE_ENTRY", 0));
            userRoles.add(new UserRole("ROLE_ACCOUNTANT", 0));

            userRoles.add(new UserRole("ROLE_UPDATE_GL_ACCOUNT_NAME", 0));
            userRoles.add(new UserRole("ROLE_CUSTOMER", 0));
            userRoles.add(new UserRole("ROLE_AGENT", 0));

            userRoles.add(new UserRole("ROLE_FIELD_COLLECTOR", 0));
            userRoles.add(new UserRole("ROLE_ACCOUNT_BALANCES", 0));
            userRoles.add(new UserRole("ROLE_MAIN_GL_ACCOUNTS", 0));

            userRoles.add(new UserRole("ROLE_DAILY_COLLECTION_CUSTOMER", 0));
            userRoles.add(new UserRole("ROLE_CREATE_GL_ACCOUNT", 0));

            userRoles.add(new UserRole("ROLE_MAIN_REPORTS", 0));
            userRoles.add(new UserRole("ROLE_MAIN_USER_MANAGEMENT", 0));
            userRoles.add(new UserRole("ROLE_MAIN_ACCOUNT_MANAGEMENT", 0));

            userRoles.add(new UserRole("ROLE_AUDITOR", 0));
            userRoles.add(new UserRole("ROLE_BOARD_MEMBER", 0));
            userRoles.add(new UserRole("ROLE_MAIN_USER_STATUS", 0));

            userRoles.add(new UserRole("ROLE_ACCOUNT_CLERK", 0));
            userRoles.add(new UserRole("ROLE_ADMIN", 0));
            userRoles.add(new UserRole("ROLE_MAIN_ISSUES", 0));
            userRoles.add(new UserRole("ROLE_MAIN_POS", 0));
            userRoles.add(new UserRole("ROLE_MAIN_ECOMMERCE", 0));
            userRoles.add(new UserRole("ROLE_MAIN_CALL_CENTER", 0));

            userRoles.add(new UserRole("ROLE_MAIN_FREE", 0));
            userRoles.add(new UserRole("ROLE_MAIN_FREE_2", 0));

            userRoles.add(new UserRole("ROLE_EMPLOYEE", 0));

            userRoleRepository.saveAll(userRoles);

        }else{
            System.out.println("---------------     USER_ROLES ---  Nothing updated     -----------------");
            System.out.println("---------------          ---     -----------------");

        }
    }


    public List<RuntimeProperties> initSystem(long orgId) {

//        initSystemService.initUserRoles();

        List<RuntimeProperties> runtimePropertiesList = new ArrayList<RuntimeProperties>();
        if (!runtimePropertiesRepository.findByOrgId(orgId).iterator().hasNext()) {

            RuntimeProperties businessName = new RuntimeProperties();
            businessName.setPropertyName("Business Name");
            businessName.setPropertyValue("bitsvalley");
            businessName.setOrgId(orgId);
            runtimePropertiesList.add(businessName);

            RuntimeProperties uploadDirectory = new RuntimeProperties();
            uploadDirectory.setPropertyName("Upload Directory");
            uploadDirectory.setPropertyValue("/User/abcUser/");
            uploadDirectory.setOrgId(orgId);
            runtimePropertiesList.add(uploadDirectory);

            RuntimeProperties momoOrgAccount = new RuntimeProperties();
            momoOrgAccount.setPropertyName("momoOrgAccount");
            momoOrgAccount.setPropertyValue("222222222222222222");
            momoOrgAccount.setOrgId(orgId);
            runtimePropertiesList.add(momoOrgAccount);

            RuntimeProperties imagePrefix = new RuntimeProperties();
            imagePrefix.setPropertyName("Image Prefix");
            imagePrefix.setPropertyValue("file:/");
            imagePrefix.setOrgId(orgId);
            runtimePropertiesList.add(imagePrefix);

            RuntimeProperties slogan = new RuntimeProperties();
            slogan.setPropertyName("Slogan");
            slogan.setPropertyValue("together we achieve more");
            slogan.setOrgId(orgId);
            runtimePropertiesList.add(slogan);

            RuntimeProperties currency = new RuntimeProperties();
            currency.setPropertyName("Currency");
            currency.setPropertyValue("Frs CFA");
            currency.setOrgId(orgId);
            runtimePropertiesList.add(currency);

            RuntimeProperties logo = new RuntimeProperties();
            logo.setPropertyName("logo");
            logo.setPropertyValue("/Users/images/logo.png");
            logo.setOrgId(orgId);
            runtimePropertiesList.add(logo);

            RuntimeProperties unionLogo = new RuntimeProperties();
            unionLogo.setPropertyName("unionLogo");
            unionLogo.setPropertyValue("/Users/images/unionLogo.png");
            unionLogo.setOrgId(orgId);
            runtimePropertiesList.add(unionLogo);

            RuntimeProperties address = new RuntimeProperties();
            address.setPropertyName("address");
            address.setPropertyValue("123 Main street");
            address.setOrgId(orgId);
            runtimePropertiesList.add(address);

            RuntimeProperties telephone = new RuntimeProperties();
            telephone.setPropertyName("telephone");
            telephone.setPropertyValue("675 879 345");
            telephone.setOrgId(orgId);
            runtimePropertiesList.add(telephone);

            RuntimeProperties telephone2 = new RuntimeProperties();
            telephone2.setPropertyName("telephone2");
            telephone2.setPropertyValue("665 879 345");
            telephone2.setOrgId(orgId);
            runtimePropertiesList.add(telephone2);

            RuntimeProperties email = new RuntimeProperties();
            email.setPropertyName("email");
            email.setPropertyValue("info@bitsvalley.com");
            email.setOrgId(orgId);
            runtimePropertiesList.add(email);

            RuntimeProperties website = new RuntimeProperties();
            website.setPropertyName("website");
            website.setPropertyValue("www.bitsvalley.com");
            website.setOrgId(orgId);
            runtimePropertiesList.add(website);

            RuntimeProperties fax = new RuntimeProperties();
            fax.setPropertyName("fax");
            fax.setPropertyValue("665 879 345");
            fax.setOrgId(orgId);
            runtimePropertiesList.add(fax);

            RuntimeProperties logoSize = new RuntimeProperties();
            logoSize.setPropertyName("logoSize");
            logoSize.setPropertyValue("50");
            logoSize.setOrgId(orgId);
            runtimePropertiesList.add(logoSize);

            RuntimeProperties themeColor = new RuntimeProperties();
            themeColor.setPropertyName(BVMicroUtils.THEME_COLOR);
            themeColor.setPropertyValue("green");
            themeColor.setOrgId(orgId);
            runtimePropertiesList.add(themeColor);

            RuntimeProperties themeColor2 = new RuntimeProperties();
            themeColor2.setPropertyName(BVMicroUtils.THEME_COLOR_2);
            themeColor2.setPropertyValue("gray");
            themeColor2.setOrgId(orgId);
            runtimePropertiesList.add(themeColor2);

            RuntimeProperties countryCode = new RuntimeProperties();
            countryCode.setPropertyName(BVMicroUtils.COUNTRY_CODE);
            countryCode.setPropertyValue("countryCode");
            countryCode.setOrgId(orgId);
            runtimePropertiesList.add(countryCode);

            RuntimeProperties vatPercent = new RuntimeProperties();
            vatPercent.setPropertyName("vatPercent");
            vatPercent.setPropertyValue("0.1925");
            vatPercent.setOrgId(orgId);
            runtimePropertiesList.add(vatPercent);

            RuntimeProperties unitSharePrice = new RuntimeProperties();
            unitSharePrice.setPropertyName(BVMicroUtils.UNIT_SHARE_PRICE);
            unitSharePrice.setPropertyValue("10000");
            unitSharePrice.setOrgId(orgId);
            runtimePropertiesList.add(unitSharePrice);

            RuntimeProperties unitPreferenceSharePrice = new RuntimeProperties();
            unitPreferenceSharePrice.setPropertyName(BVMicroUtils.UNIT_SHARE_PREFERENCE_PRICE);
            unitPreferenceSharePrice.setPropertyValue("20000");
            unitPreferenceSharePrice.setOrgId(orgId);
            runtimePropertiesList.add(unitPreferenceSharePrice);

            RuntimeProperties currentAccount = new RuntimeProperties();
            currentAccount.setPropertyName(BVMicroUtils.CURRENT_ACCOUNT);
            currentAccount.setPropertyValue("FREE VERSION");
            currentAccount.setOrgId(orgId);
            runtimePropertiesList.add(currentAccount);

            RuntimeProperties apartmentAccount = new RuntimeProperties();
            apartmentAccount.setPropertyName(BVMicroUtils.APARTMENT_ACCOUNT);
            apartmentAccount.setPropertyValue("FREE VERSION");
            apartmentAccount.setOrgId(orgId);
            runtimePropertiesList.add(apartmentAccount);

            RuntimeProperties savingAccount = new RuntimeProperties();
            savingAccount.setPropertyName(BVMicroUtils.SAVING_ACCOUNT);
            savingAccount.setPropertyValue("FREE VERSION");
            savingAccount.setOrgId(orgId);
            runtimePropertiesList.add(savingAccount);

            RuntimeProperties shareAccount = new RuntimeProperties();
            shareAccount.setPropertyName(BVMicroUtils.SHARE_ACCOUNT);
            shareAccount.setPropertyValue("FREE VERSION");
            shareAccount.setOrgId(orgId);
            runtimePropertiesList.add(shareAccount);

            RuntimeProperties loanAccount = new RuntimeProperties();
            loanAccount.setPropertyName(BVMicroUtils.LOAN_ACCOUNT);
            loanAccount.setPropertyValue("FREE VERSION");
            loanAccount.setOrgId(orgId);
            runtimePropertiesList.add(currentAccount);


            RuntimeProperties billSelectionEnabled = new RuntimeProperties();
            billSelectionEnabled.setPropertyName(BVMicroUtils.BILL_SELECTION_ENABLED);
            billSelectionEnabled.setPropertyValue("false");
            billSelectionEnabled.setOrgId(orgId);
            runtimePropertiesList.add(billSelectionEnabled);

            RuntimeProperties makeAPayment = new RuntimeProperties();
            makeAPayment.setPropertyName(BVMicroUtils.MAKE_A_PAYMENT);
            makeAPayment.setPropertyValue("makeAPayment");
            makeAPayment.setOrgId(orgId);
            runtimePropertiesList.add(makeAPayment);

            RuntimeProperties bid = new RuntimeProperties();
            bid.setPropertyName(BVMicroUtils.BID);
            bid.setPropertyValue("bid");
            bid.setOrgId(orgId);
            runtimePropertiesList.add(bid);

            RuntimeProperties invoiceFooter = new RuntimeProperties();
            invoiceFooter.setPropertyName(BVMicroUtils.INVOICE_FOOTER);
            invoiceFooter.setPropertyValue("Pay your invoice and secure your reservation at our Ecobank account");
            invoiceFooter.setOrgId(orgId);
            runtimePropertiesList.add(invoiceFooter);

            RuntimeProperties contextName = new RuntimeProperties();
            contextName.setPropertyName(BVMicroUtils.CONTEXT_NAME);
            contextName.setPropertyValue("Customer");
            contextName.setOrgId(orgId);
            runtimePropertiesList.add(contextName);


            Iterable<RuntimeProperties> runtimePropertiesListIterable = runtimePropertiesList;
            runtimePropertiesRepository.saveAll(runtimePropertiesListIterable);
            System.out.println("------------STARTED ---     UPDATE RUNTIME SETTINGS      -----------------");

        }
        return runtimePropertiesList;
    }



    public RuntimeSetting findByBid(String bidValue) {
        List<RuntimeSetting> list = new ArrayList<RuntimeSetting>();
        RuntimeProperties properties = runtimePropertiesRepository.findByBid("bid", bidValue);
        Iterable<RuntimeProperties> all = runtimePropertiesRepository.findByOrgId(properties.getOrgId());

        Iterator<RuntimeProperties> iterator = all.iterator();
        RuntimeSetting runtime = new RuntimeSetting();
        while (iterator.hasNext()) {
            RuntimeProperties rp = iterator.next();
            if (rp.getPropertyName().equals("Business Name")) {
                runtime.setBusinessName(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("Slogan")) {
                runtime.setSlogan(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.NOTES)) {
            } else if (rp.getPropertyName().equals("OrgId")) {
                runtime.setOrgId(Long.parseLong(rp.getPropertyValue()));
            } else if (rp.getPropertyName().equals("Image Prefix")) {
                runtime.setImagePrefix(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("logo")) {
                runtime.setLogo(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("unionLogo")) {
                runtime.setUnionLogo(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("address")) {
                runtime.setAddress(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("telephone1")) {
                runtime.setTelephone(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("telephone2")) {
                runtime.setTelephone2(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("email")) {
                runtime.setEmail(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("countryCode")) {
                runtime.setCountryCode(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("fax")) {
                runtime.setFax(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("logoSize")) {
                runtime.setLogoSize(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.THEME_COLOR)) {
                runtime.setThemeColor(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.THEME_COLOR_2)) {
                runtime.setThemeColor2(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.BID)) {
                runtime.setBid(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.INVOICE_FOOTER)) {
                runtime.setInvoiceFooter(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.CURRENCY)) {
                runtime.setCurrency(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.WALLET_ENABLED)) {
                runtime.setWalletEnabled(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.CONTEXT_NAME)) {
                runtime.setContextName(rp.getPropertyValue());
            }
        }
        return runtime;
    }


    public RuntimeSetting findByOrgId(long orgId) {
        Iterable<RuntimeProperties> all = runtimePropertiesRepository.findByOrgId(orgId);
        Iterator<RuntimeProperties> iterator = all.iterator();
        RuntimeSetting runtime = new RuntimeSetting();
        while (iterator.hasNext()) {
            RuntimeProperties rp = iterator.next();
            if (rp.getPropertyName().equals("Business Name")) {
                runtime.setBusinessName(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("Slogan")) {
                runtime.setSlogan(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.NOTES)) {
                runtime.setNotes(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("OrgId")) {
                runtime.setOrgId(Long.parseLong(rp.getPropertyValue()));
            } else if (rp.getPropertyName().equals("Image Prefix")) {
                runtime.setImagePrefix(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("logo")) {
                runtime.setLogo(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("countryCode")) {
                runtime.setCountryCode(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("website")) {
                runtime.setWebsite(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("unionLogo")) {
                runtime.setUnionLogo(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("address")) {
                runtime.setAddress(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("telephone1")) {
                runtime.setTelephone(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("telephone2")) {
                runtime.setTelephone2(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("email")) {
                runtime.setEmail(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("currency")) {
                runtime.setCurrency(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("fax")) {
                runtime.setFax(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("logoSize")) {
                runtime.setLogoSize(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.THEME_COLOR)) {
                runtime.setThemeColor(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.THEME_COLOR_2)) {
                runtime.setThemeColor2(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("vatPercent")) {
                runtime.setVatPercent(new Double(rp.getPropertyValue()));
            } else if (rp.getPropertyName().equals(BVMicroUtils.UNIT_SHARE_PRICE)) {
                runtime.setUnitSharePrice(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.UNIT_SHARE_PREFERENCE_PRICE)) {
                runtime.setUnitPreferenceSharePrice(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("Upload Directory")) {
                runtime.setUploadDirectory(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals("momoOrgAccount")) {
                runtime.setMomoOrgAccount(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.CURRENT_ACCOUNT)) {
                runtime.setCurrentAccount(rp.getPropertyValue().trim());
            } else if (rp.getPropertyName().equals(BVMicroUtils.SAVING_ACCOUNT)) {
                runtime.setSavingAccount(rp.getPropertyValue().trim());
            } else if (rp.getPropertyName().equals(BVMicroUtils.DAILY_SAVING_ACCOUNT)) {
                runtime.setDailySavingAccount(rp.getPropertyValue().trim());
            } else if (rp.getPropertyName().equals(BVMicroUtils.SHARE_ACCOUNT)) {
                runtime.setShareAccount(rp.getPropertyValue().trim());
            } else if (rp.getPropertyName().equals(BVMicroUtils.LOAN_ACCOUNT)) {
                runtime.setLoanAccount(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.APARTMENT_ACCOUNT)) {
                runtime.setApartmentAccount(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.MAKE_A_PAYMENT)) {
                runtime.setMakeAPayment(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.CURRENCY)) {
                runtime.setCurrency(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.BID) && (rp.getPropertyValue() != null)) {
                runtime.setBid(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.INVOICE_FOOTER) && rp.getPropertyValue() != null) {
                runtime.setInvoiceFooter(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.BILL_SELECTION_ENABLED)) {
                runtime.setBillSelectionEnabled(rp.getPropertyValue());
            } else if (rp.getPropertyName().equals(BVMicroUtils.CONTEXT_NAME)) {
                runtime.setContextName(rp.getPropertyValue());
            }else if (rp.getPropertyName().equals(BVMicroUtils.PLATFORM_FEE)) {
                runtime.setPlatformFee(Double.parseDouble(rp.getPropertyValue()));
            }else if (rp.getPropertyName().equals(BVMicroUtils.WALLET_ENABLED)) {
                runtime.setWalletEnabled(rp.getPropertyValue());
            }else if (rp.getPropertyName().equals(BVMicroUtils.SAVING_MIN_BALANCE)) {
                runtime.setSavingMinBalance(Double.parseDouble(rp.getPropertyValue()));
            }else if (rp.getPropertyName().equals(BVMicroUtils.EMAIL_DESCRIPTION_1)) {
                runtime.setEmailDescription1((rp.getPropertyValue()));
            }else if (rp.getPropertyName().equals(BVMicroUtils.EMAIL_DESCRIPTION_2)) {
                runtime.setEmailDescription2((rp.getPropertyValue()));
            }else if (rp.getPropertyName().equals(BVMicroUtils.ORG_PROVIDED_SERVICES)) {
                runtime.setOrganizationProvidedServices((rp.getPropertyValue()));
            }
        }
        return runtime;
    }

    public String findByPropertyName(String logo, long orgId) {
        if(runtimePropertiesRepository.findByPropertyNameAndOrgId(logo, orgId) == null ) return "0";
        return runtimePropertiesRepository.findByPropertyNameAndOrgId(logo, orgId).getPropertyValue();
    }
}
