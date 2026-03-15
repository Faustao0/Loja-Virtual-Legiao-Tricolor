package com.legiaotricolor.BackLegiaoTricolor.dto;

import jakarta.validation.constraints.NotNull;

public record UpdateItemRequestDTO(
        @NotNull Integer quantity
) {}
