package com.legiaotricolor.BackLegiaoTricolor.repository;

import com.legiaotricolor.BackLegiaoTricolor.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
}
