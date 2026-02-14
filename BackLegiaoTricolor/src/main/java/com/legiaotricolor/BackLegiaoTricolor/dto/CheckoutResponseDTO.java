package com.legiaotricolor.BackLegiaoTricolor.dto;

import com.legiaotricolor.BackLegiaoTricolor.enums.PaymentStatus;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckoutResponseDTO {

    private UUID paymentId;
    private PaymentStatus status;
    private String message;

    // PIX
    private String pixQrCode;
    private String pixCopyPaste;
}
