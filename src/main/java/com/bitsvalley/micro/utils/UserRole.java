package com.bitsvalley.micro.utils;

public enum UserRole {


    ROLE_AGENT("ROLE_AGENT"), ROLE_BOARD_MEMBER("ROLE_BOARD_MEMBER"), ROLE_MANAGER("ROLE_MANAGER"), ROLE_CUSTOMER("ROLE_CUSTOMER"), ROLE_AUDITOR("ROLE_AUDITOR");

    private final String displayValue;

    private UserRole(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

}
