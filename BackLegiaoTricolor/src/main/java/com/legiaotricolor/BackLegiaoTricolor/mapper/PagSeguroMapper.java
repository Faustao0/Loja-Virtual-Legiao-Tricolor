package com.legiaotricolor.BackLegiaoTricolor.mapper;

import com.legiaotricolor.BackLegiaoTricolor.domain.Payment;
import com.legiaotricolor.BackLegiaoTricolor.dto.CheckoutRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.PagSeguroRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.enums.PaymentMethod;

import java.math.BigDecimal;

public class PagSeguroMapper {

    private PagSeguroMapper() {
    }

    public static PagSeguroRequestDTO toRequest(
            Payment payment,
            CheckoutRequestDTO dto
    ) {

        PagSeguroRequestDTO request = new PagSeguroRequestDTO();

        // reference_id
        request.setReferenceId(payment.getId().toString());

        // amount
        PagSeguroRequestDTO.Amount amount =
                new PagSeguroRequestDTO.Amount();

        amount.setValue(
                payment.getAmount()
                        .multiply(BigDecimal.valueOf(100))
                        .intValue()
        );

        request.setAmount(amount);

        // payment_method
        PagSeguroRequestDTO.PaymentMethodDTO pm =
                new PagSeguroRequestDTO.PaymentMethodDTO();

        pm.setType(payment.getMethod().name());

        if (payment.getMethod() == PaymentMethod.CREDIT_CARD) {
            pm.setInstallments(dto.getInstallments());

            PagSeguroRequestDTO.Card card =
                    new PagSeguroRequestDTO.Card();
            card.setToken(dto.getCardToken());

            pm.setCard(card);
        } else {
            pm.setInstallments(1);
        }

        request.setPaymentMethod(pm);

        return request;
    }
}