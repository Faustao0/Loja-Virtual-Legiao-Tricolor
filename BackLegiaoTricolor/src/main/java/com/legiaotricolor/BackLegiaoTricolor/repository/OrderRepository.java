package com.legiaotricolor.BackLegiaoTricolor.repository;

import com.legiaotricolor.BackLegiaoTricolor.domain.Order;
import com.legiaotricolor.BackLegiaoTricolor.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByUserId(UUID userId);

    Optional<Order> findByUserIdAndStatus(UUID userId, OrderStatus status);
}
