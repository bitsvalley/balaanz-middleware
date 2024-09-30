package com.bitsvalley.micro.utils;

public enum TransactionStatus {

    ACTIVE("ACTIVE"), DELETED("APPROVED"),VOID("VOID"), REFUND("REFUND"), SUSPENDED("SUSPENDED"), DEFAULTED("DEFAULTED"), IN_ACTIVE("IN_ACTIVE"), PENDING_APPROVAL("PENDING_APPROVAL"), REJECTED("REJECTED");

    private TransactionStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    private final String displayValue;

}
