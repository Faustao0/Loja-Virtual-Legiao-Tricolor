package com.legiaotricolor.BackLegiaoTricolor.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class PagSeguroRequestDTO {

    @JsonProperty("reference_id")
    private String referenceId;

    private Amount amount;

    @JsonProperty("payment_method")
    private PaymentMethodDTO paymentMethod;

    @Data
    public static class Amount {
        private Integer value; // centavos
        private String currency = "BRL";
    }

    @Data
    public static class PaymentMethodDTO {
        private String type; // PIX, CREDIT_CARD, DEBIT_CARD
        private Integer installments;
        private Card card;
    }

    @Data
    public static class Card {
        private String token;
    }
}