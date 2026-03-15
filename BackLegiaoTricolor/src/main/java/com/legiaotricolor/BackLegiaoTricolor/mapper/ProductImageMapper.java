package com.legiaotricolor.BackLegiaoTricolor.mapper;

import com.legiaotricolor.BackLegiaoTricolor.domain.ProductImage;
import com.legiaotricolor.BackLegiaoTricolor.dto.ProductImageDTO;

public class ProductImageMapper {

    private ProductImageMapper() {}

    public static ProductImageDTO toDTO(ProductImage image) {
        return new ProductImageDTO(
                image.getId(),
                image.getUrl(),
                image.getMainImage()
        );
    }
}
