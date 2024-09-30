package com.bitsvalley.micro.domain;

import com.bitsvalley.micro.utils.AccountStatus;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Fru Chifen
 * 09.07.2021
 */
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(unique = true)
    private String userName;
    private String customerNumber;
    private String dailyCustomerNumber;
    private String firstName;
    private String lastName;
    private String gender;
    private String profession;
    private String telephone1;
    private String telephone2;
    private String address;
    private String password;
    private String notes;
    private String referral;
    private String email;
    private String dateOfBirth;
    private String idFilePath;
    private String idFilePath2;
    private String idFilePath3;
    private String idFilePath4;
    private String identityCardNumber;
    private AccountStatus accountStatus;
    private String terminalCode = "000000";
    private boolean receiveEmailNotifications;

    @Column(precision = 9, scale = 6)
    private BigDecimal longitude;
    @Column(precision = 9, scale = 6)
    private BigDecimal latitude;


    @OneToOne(cascade = CascadeType.ALL)
    private UserControl userControl;

    @ManyToOne(cascade = CascadeType.ALL)
    private Branch branch;
//    @Column(name="orgId", columnDefinition="default '0'")

    @Column(name = "orgId", nullable = false, columnDefinition = "bigint default 0")
    private long orgId = 0;

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    @ManyToMany(cascade = CascadeType.ALL)
    private List<UserRole> userRole;

    @OneToMany(cascade = CascadeType.ALL)
    private List<DailySavingAccount> dailySavingAccount = new ArrayList<DailySavingAccount>();

    @OneToMany(cascade = CascadeType.ALL)
    private List<Beneficiary> beneficiary = new ArrayList<Beneficiary>();

    private LocalDateTime accountExpiredDate;
    private boolean accountLocked;

    private LocalDateTime accountBlockedDate;
    private boolean accountExpired;
    private String identityCardExpiry;
    private Date lastUpdated;
    private Date created;
    private String createdBy;

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getTelephone1() {
        return telephone1;
    }

    public void setTelephone1(String telephone1) {
        this.telephone1 = telephone1;
    }

    public String getTelephone2() {
        return telephone2;
    }

    public void setTelephone2(String telephone2) {
        this.telephone2 = telephone2;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<UserRole> getUserRole() {
        return userRole;
    }

    public void setUserRole(List<UserRole> userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<DailySavingAccount> getDailySavingAccount() {
        return dailySavingAccount;
    }

    public void setDailySavingAccount(List<DailySavingAccount> dailySavingAccount) {
        this.dailySavingAccount = dailySavingAccount;
    }

    public LocalDateTime getAccountExpiredDate() {
        return accountExpiredDate;
    }

    public void setAccountExpiredDate(LocalDateTime accountExpiredDate) {
        this.accountExpiredDate = accountExpiredDate;
    }

    public LocalDateTime getAccountBlockedDate() {
        return accountBlockedDate;
    }

    public void setAccountBlockedDate(LocalDateTime accountBlockedDate) {
        this.accountBlockedDate = accountBlockedDate;
    }

    public boolean isAccountExpired() {
        return accountExpired;
    }

    public void setAccountExpired(boolean accountExpired) {
        this.accountExpired = accountExpired;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isAccountLocked() {
        return accountLocked;
    }

    public void setAccountLocked(boolean accountLocked) {
        this.accountLocked = accountLocked;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }


    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getIdFilePath() {
        return idFilePath;
    }

    public void setIdFilePath(String idFilePath) {
        this.idFilePath = idFilePath;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getIdentityCardNumber() {
        return identityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        this.identityCardNumber = identityCardNumber;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public List<Beneficiary> getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(List<Beneficiary> beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getIdentityCardExpiry() {
        return identityCardExpiry;
    }

    public void setIdentityCardExpiry(String identityCardExpiry) {
        this.identityCardExpiry = identityCardExpiry;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getIdFilePath2() {
        return idFilePath2;
    }

    public void setIdFilePath2(String idFilePath2) {
        this.idFilePath2 = idFilePath2;
    }

    public String getIdFilePath3() {
        return idFilePath3;
    }

    public void setIdFilePath3(String idFilePath3) {
        this.idFilePath3 = idFilePath3;
    }

    public String getIdFilePath4() {
        return idFilePath4;
    }

    public void setIdFilePath4(String idFilePath4) {
        this.idFilePath4 = idFilePath4;
    }

    public void setDailyCustomerNumber(String dailyCustomerNumber) {
        this.dailyCustomerNumber = dailyCustomerNumber;
    }

    public String getDailyCustomerNumber() {
        return dailyCustomerNumber;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    public UserControl getUserControl() {
        return userControl;
    }

    public String getReferral() {
        return referral;
    }

    public void setReferral(String referral) {
        this.referral = referral;
    }

    public void setUserControl(UserControl userControl) {
        this.userControl = userControl;
    }

    public String getTerminalCode() {
        return terminalCode;
    }

    public void setTerminalCode(String terminalCode) {
        this.terminalCode = terminalCode;
    }

    public boolean isReceiveEmailNotifications() {
        return receiveEmailNotifications;
    }

    public void setReceiveEmailNotifications(boolean receiveEmailNotifications) {
        this.receiveEmailNotifications = receiveEmailNotifications;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }
}
