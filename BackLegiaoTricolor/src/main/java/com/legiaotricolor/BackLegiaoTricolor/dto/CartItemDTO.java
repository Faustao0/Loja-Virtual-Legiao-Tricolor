package com.legiaotricolor.BackLegiaoTricolor.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record CartItemDTO(
        UUID itemId,
        UUID productId,
        String productName,
        BigDecimal price,
        Integer quantity,
        BigDecimal subtotal
) {}

