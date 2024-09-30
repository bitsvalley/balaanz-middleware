package com.bitsvalley.micro.utils;

public enum BranchStatus {

    ACTIVE("ACTIVE"), DELETED("DELETED"), IN_ACTIVE("IN_ACTIVE"), NEW("NEW");

    private BranchStatus(String displayValue) {
        this.displayValue = displayValue;
    }

    private final String displayValue;

}
