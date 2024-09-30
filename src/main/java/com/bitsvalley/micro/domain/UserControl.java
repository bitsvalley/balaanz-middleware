package com.bitsvalley.micro.domain;

import javax.persistence.*;

/**
 * @author Fru Chifen
 * 10.11.2021
 */
@Entity
@Table(name = "usercontrol")
public class UserControl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private double mobileMoneyDailyLimit = 10000;
    private double mobileMoneyMonthlyLimit = 100000;
    private boolean mobileMoneyActive;
    private double curr2currLimit = 10000;
    private boolean curr2currActive;
    private boolean sendDailyCollectSMS;

    private double notSignedCollectionLimit = 100000;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getMobileMoneyDailyLimit() {
        return mobileMoneyDailyLimit;
    }

    public void setMobileMoneyDailyLimit(double mobileMoneyDailyLimit) {
        this.mobileMoneyDailyLimit = mobileMoneyDailyLimit;
    }

    public double getMobileMoneyMonthlyLimit() {
        return mobileMoneyMonthlyLimit;
    }

    public void setMobileMoneyMonthlyLimit(double mobileMoneyMonthlyLimit) {
        this.mobileMoneyMonthlyLimit = mobileMoneyMonthlyLimit;
    }

    public boolean isMobileMoneyActive() {
        return mobileMoneyActive;
    }

    public void setMobileMoneyActive(boolean mobileMoneyActive) {
        this.mobileMoneyActive = mobileMoneyActive;
    }

    public double getCurr2currLimit() {
        return curr2currLimit;
    }

    public void setCurr2currLimit(double curr2currLimit) {
        this.curr2currLimit = curr2currLimit;
    }

    public boolean isCurr2currActive() {
        return curr2currActive;
    }

    public void setCurr2currActive(boolean curr2currActive) {
        this.curr2currActive = curr2currActive;
    }

    public double getNotSignedCollectionLimit() {
        return notSignedCollectionLimit;
    }

    public void setNotSignedCollectionLimit(double notSignedCollectionLimit) {
        this.notSignedCollectionLimit = notSignedCollectionLimit;
    }

    public boolean isSendDailyCollectSMS() {
        return sendDailyCollectSMS;
    }

    public void setSendDailyCollectSMS(boolean sendDailyCollectSMS) {
        this.sendDailyCollectSMS = sendDailyCollectSMS;
    }
}