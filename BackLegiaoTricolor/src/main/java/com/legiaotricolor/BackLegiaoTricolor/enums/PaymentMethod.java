package com.legiaotricolor.BackLegiaoTricolor.enums;

public enum PaymentMethod {

    PIX("Pix"),
    CREDIT_CARD("Cartão de Crédito"),
    DEBIT_CARD("Cartão de Débito");

    private final String description;

    PaymentMethod(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
