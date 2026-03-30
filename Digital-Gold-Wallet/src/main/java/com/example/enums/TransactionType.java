package com.example.enums;

public enum TransactionType {

    CREDITED_TO_WALLET("Credited to wallet"),
    DEBITED_FROM_WALLET("Debited from wallet"),
    BUY("Buy"),
    SELL("Sell"),
    CONVERT_TO_PHYSICAL("Convert to Physical"),
    SUCCESS("Success");

    private final String value;

    TransactionType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // Convert DB value → Enum
    public static TransactionType fromValue(String value) {
        for (TransactionType type : TransactionType.values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown value: " + value);
    }
}