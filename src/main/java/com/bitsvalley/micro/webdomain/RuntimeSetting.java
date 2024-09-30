package com.bitsvalley.micro.webdomain;

public class RuntimeSetting {

    private String businessName;
    private long orgId;
    private String slogan;
    private String telephone;
    private String telephone2;
    private String logo;
    private String unionLogo;
    private String logoSize;
    private String bid;
    private long id;
    private String currency;
    private String themeColor;
    private String themeColor2;
    private String fax;
    private String email;
    private String website;
    private int noOfAccounts;
    private String address;
    private Double vatPercent;
    private String unitSharePrice;
    private String unitPreferenceSharePrice;
    private String uploadDirectory;
    private String imagePrefix;
    private String notes;
    private String currentAccount;
    private String loanAccount;
    private String shareAccount;
    private String savingAccount;
    private String dailySavingAccount;
    private String apartmentAccount;
    private String makeAPayment;
    private String billSelectionEnabled;
    private String countryCode;
    private String invoiceFooter;
    private String contextName;
    private String momoOrgAccount;
    private double platformFee;
    private String walletEnabled;
    private double savingMinBalance;

    public String getEmailDescription1() {
        return emailDescription1;
    }

    public void setEmailDescription1(String emailDescription1) {
        this.emailDescription1 = emailDescription1;
    }

    public String getEmailDescription2() {
        return emailDescription2;
    }

    public void setEmailDescription2(String emailDescription2) {
        this.emailDescription2 = emailDescription2;
    }

    private String emailDescription1;
    private String emailDescription2;
    private String organizationProvidedServices;

    public double getPlatformFee() {
        return platformFee;
    }

    public void setPlatformFee(double platformFee) {
        this.platformFee = platformFee;
    }

    public String getInvoiceFooter() {
        return invoiceFooter;
    }

    public void setInvoiceFooter(String invoiceFooter) {
        this.invoiceFooter = invoiceFooter;
    }

    public String getApartmentAccount() {
        return apartmentAccount;
    }

    public void setApartmentAccount(String apartmentAccount) {
        this.apartmentAccount = apartmentAccount;
    }

    public String getImagePrefix() {
        return imagePrefix;
    }

    public void setImagePrefix(String imagePrefix) {
        this.imagePrefix = imagePrefix;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getNoOfAccounts() {
        return noOfAccounts;
    }

    public void setNoOfAccounts(int noOfAccounts) {
        this.noOfAccounts = noOfAccounts;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLogoSize() {
        return logoSize;
    }

    public void setLogoSize(String logoSize) {
        this.logoSize = logoSize;
    }

    public String getThemeColor() {
        return themeColor;
    }

    public void setThemeColor(String themeColor) {
        this.themeColor = themeColor;
    }

    public String getThemeColor2() {
        return themeColor2;
    }

    public void setThemeColor2(String themeColor2) {
        this.themeColor2 = themeColor2;
    }

    public String getUnionLogo() {
        if(unionLogo != null) {
            unionLogo.replace("\\", "/");
        }
        return unionLogo;
    }

    public void setUnionLogo(String unionLogo) {
        this.unionLogo = unionLogo;
    }

    public String getUnitSharePrice() { return unitSharePrice; }

    public void setUnitSharePrice(String unitSharePrice) { this.unitSharePrice = unitSharePrice; }

    public Double getVatPercent() {
        return vatPercent;
    }

    public void setVatPercent(Double vatPercent) {
        this.vatPercent = vatPercent;
    }

    public String getUploadDirectory() {
        return uploadDirectory;
    }

    public void setUploadDirectory(String uploadDirectory) {
        this.uploadDirectory = uploadDirectory;
    }

    public String getCurrentAccount() {
        return currentAccount;
    }

    public void setCurrentAccount(String currentAccount) {
        this.currentAccount = currentAccount;
    }

    public String getLoanAccount() {
        return loanAccount;
    }

    public void setLoanAccount(String loanAccount) {
        this.loanAccount = loanAccount;
    }

    public String getShareAccount() {
        return shareAccount;
    }

    public void setShareAccount(String shareAccount) {
        this.shareAccount = shareAccount;
    }

    public String getSavingAccount() {
        return savingAccount;
    }

    public void setSavingAccount(String savingAccount) {
        this.savingAccount = savingAccount;
    }

    public String getDailySavingAccount() {
        return dailySavingAccount;
    }

    public void setDailySavingAccount(String dailySavingAccount) {
        this.dailySavingAccount = dailySavingAccount;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getUnitPreferenceSharePrice() {
        return unitPreferenceSharePrice;
    }

    public void setUnitPreferenceSharePrice(String unitPreferenceSharePrice) {
        this.unitPreferenceSharePrice = unitPreferenceSharePrice;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getMakeAPayment() {
        return makeAPayment;
    }

    public void setMakeAPayment(String makeAPayment) {
        this.makeAPayment = makeAPayment;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBillSelectionEnabled() {
        return billSelectionEnabled;
    }

    public void setBillSelectionEnabled(String getBillSelectionEnabled) {
        this.billSelectionEnabled = getBillSelectionEnabled;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getContextName() {
        return contextName;
    }

    public void setContextName(String contextName) {
        this.contextName = contextName;
    }

    public String getMomoOrgAccount() {
        return momoOrgAccount;
    }

    public void setMomoOrgAccount(String momoOrgAccount) {
        this.momoOrgAccount = momoOrgAccount;
    }

    public String getOrganizationProvidedServices() {
        return organizationProvidedServices;
    }

    public void setOrganizationProvidedServices(String organizationProvidedServices) {
        this.organizationProvidedServices = organizationProvidedServices;
    }

    public double getSavingMinBalance() {
        return savingMinBalance;
    }

    public void setSavingMinBalance(double savingMinBalance) {
        this.savingMinBalance = savingMinBalance;
    }

    public String getWalletEnabled() {
        return walletEnabled;
    }

    public void setWalletEnabled(String walletEnabled) {
        this.walletEnabled = walletEnabled;
    }
}
