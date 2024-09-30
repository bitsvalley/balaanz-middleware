package com.bitsvalley.micro.domain;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * @author Fru Chifen
 * 11.06.2021
 */
@Entity
@Table(name = "ledgeraccount")
public class LedgerAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<GeneralLedger> generalLedger;

    private String name;

    private String displayName;
    private boolean active;
    private String code;
    private String category;
    private String creditBalance = "Y";
    private String status = "Y";
    private String cashAccountTransfer = "Y";;
    private String interAccountTransfer = "Y";
    private String createdBy;
    private Date createdDate;
    private String cashTransaction = "Y";
    private long orgId;

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<GeneralLedger> getGeneralLedger() {
        return generalLedger;
    }

    public void setGeneralLedger(List<GeneralLedger> generalLedger) {
        this.generalLedger = generalLedger;
    }

    public String getCashAccountTransfer() {
        return cashAccountTransfer;
    }

    public void setCashAccountTransfer(String cashAccountTransfer) {
        this.cashAccountTransfer = cashAccountTransfer;
    }

    public String getCreditBalance() {
        return creditBalance;
    }

    public void setCreditBalance(String creditBalance) {
        this.creditBalance = creditBalance;
    }

    public String getInterAccountTransfer() {
        return interAccountTransfer;
    }

    public void setInterAccountTransfer(String interAccountTransfer) {
        this.interAccountTransfer = interAccountTransfer;
    }

    public String getCashTransaction() {
        return cashTransaction;
    }

    public void setCashTransaction(String cashTransaction) {
        this.cashTransaction = cashTransaction;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
