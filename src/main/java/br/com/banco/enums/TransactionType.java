package br.com.banco.enums;

public enum TransactionType {
    SAQUE("SAQUE"),
    TRANSFERENCIA("TRANSFERENCIA");

    private final String type;

    TransactionType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}