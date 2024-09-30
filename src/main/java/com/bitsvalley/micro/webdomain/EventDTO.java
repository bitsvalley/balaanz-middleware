package com.bitsvalley.micro.webdomain;

import com.bitsvalley.micro.domain.LedgerAccount;

public class EventDTO {

    String eventDescription;
    double eventAmount;
    String[] bulkCustomers;
    LedgerAccount ledgerAccount;
    long orgId;

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public double getEventAmount() {
        return eventAmount;
    }

    public void setEventAmount(double eventAmount) {
        this.eventAmount = eventAmount;
    }

    public String[] getBulkCustomers() {
        return bulkCustomers;
    }

    public void setBulkCustomers(String[] bulkCustomers) {
        this.bulkCustomers = bulkCustomers;
    }

    public LedgerAccount getLedgerAccount() {
        return ledgerAccount;
    }

    public void setLedgerAccount(LedgerAccount ledgerAccount) {
        this.ledgerAccount = ledgerAccount;
    }

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }
}
