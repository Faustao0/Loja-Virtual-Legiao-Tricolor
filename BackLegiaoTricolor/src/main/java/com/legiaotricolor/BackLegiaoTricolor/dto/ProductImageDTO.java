package com.legiaotricolor.BackLegiaoTricolor.dto;

import java.util.UUID;

public record ProductImageDTO(
        UUID id,
        String url,
        Boolean mainImage
) {}

