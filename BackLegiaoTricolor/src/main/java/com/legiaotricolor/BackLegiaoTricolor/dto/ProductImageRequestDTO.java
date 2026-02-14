package com.legiaotricolor.BackLegiaoTricolor.dto;

import jakarta.validation.constraints.NotBlank;

public record ProductImageRequestDTO(

        @NotBlank(message = "URL da imagem é obrigatória")
        String url,

        Boolean mainImage
) {}
