package com.bitsvalley.micro.utils;

public enum TransactionType {

    AVAILABLE("AVAILABLE"),
    COLLECTED("COLLECTED"),
    ON_HOLD("ON_HOLD"),
    PROCESSING("PROCESSING"),
    IN_TRANSIT("IN_TRANSIT");

    private final String transactionType;

    TransactionType(String type) {
        transactionType = type;
    }

}
