package com.bitsvalley.micro.webdomain;

import java.util.List;

public class GeneralLedgerBilanz {

    List<GeneralLedgerWeb> generalLedgerWeb;
    double creditTotal;
    double debitTotal;
    double total;

    public List<GeneralLedgerWeb> getGeneralLedgerWeb() {
        return generalLedgerWeb;
    }

    public void setGeneralLedgerWeb(List<GeneralLedgerWeb> generalLedgerWeb) {
        this.generalLedgerWeb = generalLedgerWeb;
    }

    public double getCreditTotal() {
        return creditTotal;
    }

    public void setCreditTotal(double creditTotal) {
        this.creditTotal = creditTotal;
    }

    public double getDebitTotal() {
        return debitTotal;
    }

    public void setDebitTotal(double debitTotal) {
        this.debitTotal = debitTotal;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
