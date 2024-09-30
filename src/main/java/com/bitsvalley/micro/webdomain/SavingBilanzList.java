package com.bitsvalley.micro.webdomain;

import java.util.ArrayList;
import java.util.List;

public class SavingBilanzList {

    List<SavingBilanz> savingBilanzList = new ArrayList<SavingBilanz>();
    String totalSavingInterest = "0";
    String totalSaving = "0";
    String numberOfLoanAccounts = "0";
    String numberOfRetirementSavings = "0";

    public String getTotalSavingInterest() {
        return totalSavingInterest;
    }

    public void setTotalSavingInterest(String totalSavingInterest) {
        this.totalSavingInterest = totalSavingInterest;
    }

    public List<SavingBilanz> getSavingBilanzList() {
        return savingBilanzList;
    }

    public void setSavingBilanzList(List<SavingBilanz> savingBilanzList) {
        this.savingBilanzList = savingBilanzList;
    }

    public String getTotalSaving() {
        return totalSaving;
    }

    public void setTotalSaving(String totalSaving) {
        this.totalSaving = totalSaving;
    }

    public String getNumberOfLoanAccounts() {
        return numberOfLoanAccounts;
    }

    public void setNumberOfLoanAccounts(String numberOfLoanAccounts) {
        this.numberOfLoanAccounts = numberOfLoanAccounts;
    }

    public String getNumberOfRetirementSavings() {
        return numberOfRetirementSavings;
    }

    public void setNumberOfRetirementSavings(String numberOfRetirementSavings) {
        this.numberOfRetirementSavings = numberOfRetirementSavings;
    }

}
