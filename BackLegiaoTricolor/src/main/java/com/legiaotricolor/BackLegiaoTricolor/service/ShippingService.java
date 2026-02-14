package com.legiaotricolor.BackLegiaoTricolor.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ShippingService {

    private static final BigDecimal FIXED_SHIPPING = new BigDecimal("20.00");

    public BigDecimal calculateFixedShipping() {
        return FIXED_SHIPPING;
    }
}
