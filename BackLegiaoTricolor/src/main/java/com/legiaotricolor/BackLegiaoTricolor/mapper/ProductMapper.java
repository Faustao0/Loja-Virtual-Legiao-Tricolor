package com.legiaotricolor.BackLegiaoTricolor.mapper;

import com.legiaotricolor.BackLegiaoTricolor.domain.Product;
import com.legiaotricolor.BackLegiaoTricolor.dto.ProductResponseDTO;

public class ProductMapper {

    private ProductMapper() {
        // evita instanciação
    }

    public static ProductResponseDTO toDTO(Product product) {

        return ProductResponseDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .stockQuantity(product.getStockQuantity())
                .active(product.getActive())
                .categoryName(
                        product.getCategory() != null
                                ? product.getCategory().getName()
                                : null
                )
                .build();
    }
}
