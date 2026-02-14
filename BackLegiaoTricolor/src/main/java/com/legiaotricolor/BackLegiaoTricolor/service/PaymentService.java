package com.legiaotricolor.BackLegiaoTricolor.service;

import com.legiaotricolor.BackLegiaoTricolor.domain.Order;
import com.legiaotricolor.BackLegiaoTricolor.domain.Payment;
import com.legiaotricolor.BackLegiaoTricolor.domain.User;
import com.legiaotricolor.BackLegiaoTricolor.dto.CheckoutRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.CheckoutResponseDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.PagSeguroRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.PagSeguroResponseDTO;
import com.legiaotricolor.BackLegiaoTricolor.enums.OrderStatus;
import com.legiaotricolor.BackLegiaoTricolor.enums.PaymentMethod;
import com.legiaotricolor.BackLegiaoTricolor.enums.PaymentStatus;
import com.legiaotricolor.BackLegiaoTricolor.exception.BusinessException;
import com.legiaotricolor.BackLegiaoTricolor.gateway.PagSeguroClient;
import com.legiaotricolor.BackLegiaoTricolor.mapper.PagSeguroMapper;
import com.legiaotricolor.BackLegiaoTricolor.repository.OrderRepository;
import com.legiaotricolor.BackLegiaoTricolor.repository.PaymentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final OrderRepository orderRepository;
    private final PagSeguroClient pagSeguroClient;

    public PaymentService(
            PaymentRepository paymentRepository,
            OrderRepository orderRepository,
            PagSeguroClient pagSeguroClient
    ) {
        this.paymentRepository = paymentRepository;
        this.orderRepository = orderRepository;
        this.pagSeguroClient = pagSeguroClient;
    }

    public CheckoutResponseDTO checkout(User user, CheckoutRequestDTO dto)
            throws BusinessException {

        validateInstallments(dto);

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new BusinessException("Pedido não encontrado"));

        if (!order.getUser().getId().equals(user.getId())) {
            throw new BusinessException("Você não pode pagar este pedido");
        }

        if (order.getStatus() == OrderStatus.PAID) {
            throw new BusinessException("Pedido já está pago");
        }

        Payment payment = Payment.builder()
                .user(user)
                .order(order)
                .amount(order.getTotalAmount())
                .method(dto.getPaymentMethod())
                .installments(dto.getInstallments())
                .status(PaymentStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        paymentRepository.save(payment);

        PagSeguroRequestDTO request =
                PagSeguroMapper.toRequest(payment, dto);

        PagSeguroResponseDTO psResponse =
                pagSeguroClient.createPayment(request);

        payment.setGatewayPaymentId(psResponse.getId());
        paymentRepository.save(payment);

        CheckoutResponseDTO response = new CheckoutResponseDTO();
        response.setPaymentId(payment.getId());

        if (payment.getMethod() == PaymentMethod.PIX) {
            response.setStatus(PaymentStatus.PENDING);
            response.setMessage("PIX gerado com sucesso");
            response.setPixQrCode(
                    psResponse.getQrCodes().get(0).getText()
            );
        } else {
            response.setStatus(PaymentStatus.PENDING);
            response.setMessage(
                    "Pagamento enviado para processamento"
            );
        }

        return response;
    }

    private void validateInstallments(CheckoutRequestDTO dto)
            throws BusinessException {

        if (dto.getPaymentMethod() == PaymentMethod.CREDIT_CARD) {

            if (dto.getInstallments() == null || dto.getInstallments() < 1) {
                throw new BusinessException(
                        "Número de parcelas é obrigatório para cartão de crédito"
                );
            }

            if (dto.getInstallments() > 10) {
                throw new BusinessException(
                        "Parcelamento máximo permitido é 10x"
                );
            }

        } else if (dto.getInstallments() != null) {
            throw new BusinessException(
                    "Parcelamento só é permitido no cartão de crédito"
            );
        }
    }
}

