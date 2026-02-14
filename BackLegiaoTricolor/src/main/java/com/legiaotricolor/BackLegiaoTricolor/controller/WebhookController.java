package com.legiaotricolor.BackLegiaoTricolor.controller;

import com.legiaotricolor.BackLegiaoTricolor.domain.Payment;
import com.legiaotricolor.BackLegiaoTricolor.dto.PagSeguroWebhookDTO;
import com.legiaotricolor.BackLegiaoTricolor.enums.PaymentStatus;
import com.legiaotricolor.BackLegiaoTricolor.exception.BusinessException;
import com.legiaotricolor.BackLegiaoTricolor.repository.PaymentRepository;
import com.legiaotricolor.BackLegiaoTricolor.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/webhooks/pagseguro")
@RequiredArgsConstructor
public class WebhookController {

    private final PaymentRepository paymentRepository;
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<Void> handlePagSeguroWebhook(
            @RequestBody PagSeguroWebhookDTO dto
    ) {

        Payment payment = paymentRepository.findById(
                UUID.fromString(dto.getReferenceId())
        ).orElseThrow(() ->
                new BusinessException("Pagamento não encontrado")
        );

        // Evita processar duas vezes
        if (payment.getStatus() == PaymentStatus.PAID) {
            return ResponseEntity.ok().build();
        }

        switch (dto.getStatus()) {

            case "PAID" -> {
                payment.setStatus(PaymentStatus.PAID);
                paymentRepository.save(payment);

                // CONFIRMA PAGAMENTO + BAIXA ESTOQUE
                orderService.confirmPayment(payment.getOrder());
            }

            case "CANCELED", "DECLINED" -> {
                payment.setStatus(PaymentStatus.CANCELED);
                paymentRepository.save(payment);
            }

            default -> {
                // Ignora status desconhecidos
            }
        }

        return ResponseEntity.ok().build();
    }
}
