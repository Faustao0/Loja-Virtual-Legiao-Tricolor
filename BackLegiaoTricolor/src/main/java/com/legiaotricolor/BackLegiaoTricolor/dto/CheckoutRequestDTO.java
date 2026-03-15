package com.legiaotricolor.BackLegiaoTricolor.dto;

import com.legiaotricolor.BackLegiaoTricolor.enums.PaymentMethod;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckoutRequestDTO {

    @NotNull
    private PaymentMethod paymentMethod;

    @NotNull
    private UUID orderId;

    // Para cartão (token gerado no frontend)
    private String cardToken;

    // Parcelas (somente crédito)
    @Min(value = 1, message = "Parcelas devem ser no mínimo 1")
    private Integer installments;
}
