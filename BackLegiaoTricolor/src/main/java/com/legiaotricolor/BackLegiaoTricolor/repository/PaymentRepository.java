package com.legiaotricolor.BackLegiaoTricolor.repository;

import com.legiaotricolor.BackLegiaoTricolor.domain.Payment;
import com.legiaotricolor.BackLegiaoTricolor.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    List<Payment> findByUserId(UUID userId);

    List<Payment> findByOrderId(UUID orderId);

    Optional<Payment> findByGatewayPaymentId(String gatewayPaymentId);

    List<Payment> findByStatus(PaymentStatus status);
}
