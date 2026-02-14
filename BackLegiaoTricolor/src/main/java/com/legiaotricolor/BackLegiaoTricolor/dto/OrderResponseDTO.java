package com.legiaotricolor.BackLegiaoTricolor.dto;

import com.legiaotricolor.BackLegiaoTricolor.enums.OrderStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record OrderResponseDTO(
        UUID orderId,
        OrderStatus status,
        BigDecimal totalAmount,
        List<OrderItemResponseDTO> items
) {}

