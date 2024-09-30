package com.bitsvalley.micro.webdomain;

import com.bitsvalley.micro.domain.LedgerAccount;

import java.util.Date;

/**
 * @author Fru Chifen
 * 21.08.2021
 */
public class GeneralLedgerWeb {
        private String userId;
        private long id;
        private String type;
        private String reference;
        private String accountNumber;
        private String createdBy;
        private String lastUpdatedBy;
        private String representative;
        private Date createdDate;
        private Date recordedDate;
        private Date lastUpdatedDate;
        private double amount;
        private Date date;
        private String notes;
        private int glClass;
        private double currentTotal;
        private LedgerAccount ledgerAccount;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
            this.createdBy = createdBy;
        }

        public String getLastUpdatedBy() {
            return lastUpdatedBy;
        }

        public void setLastUpdatedBy(String lastUpdatedBy) {
            this.lastUpdatedBy = lastUpdatedBy;
        }

        public Date getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(Date createdDate) {
            this.createdDate = createdDate;
        }

        public Date getLastUpdatedDate() {
            return lastUpdatedDate;
        }

        public void setLastUpdatedDate(Date lastUpdatedDate) {
            this.lastUpdatedDate = lastUpdatedDate;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public String getNotes() {
            return notes;
        }

        public void setNotes(String notes) {
            this.notes = notes;
        }


        public String getAccountNumber() {
            return accountNumber;
        }

        public void setAccountNumber(String accountNumber) {
            this.accountNumber = accountNumber;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public int getGlClass() {
            return glClass;
        }

        public void setGlClass(int glClass) {
            this.glClass = glClass;
        }

        public double getCurrentTotal() {
            return currentTotal;
        }

        public void setCurrentTotal(double currentTotal) {
            this.currentTotal = currentTotal;
        }

    public LedgerAccount getLedgerAccount() {
        return ledgerAccount;
    }

    public void setLedgerAccount(LedgerAccount ledgerAccount) {
        this.ledgerAccount = ledgerAccount;
    }

    public String getRepresentative() {
        return representative;
    }

    public void setRepresentative(String representative) {
        this.representative = representative;
    }

    public Date getRecordedDate() {
        return recordedDate;
    }

    public void setRecordedDate(Date recordedDate) {
        this.recordedDate = recordedDate;
    }
}
