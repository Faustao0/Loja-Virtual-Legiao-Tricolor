package com.legiaotricolor.BackLegiaoTricolor.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class ProductRequestDTO {

    @NotNull
    private String name;

    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private Integer stockQuantity;

    @NotNull
    private UUID categoryId;
}
