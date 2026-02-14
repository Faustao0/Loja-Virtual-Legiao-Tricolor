package com.legiaotricolor.BackLegiaoTricolor.controller;

import com.legiaotricolor.BackLegiaoTricolor.dto.ProductImageDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.ProductImageRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.exception.BusinessException;
import com.legiaotricolor.BackLegiaoTricolor.service.ProductImageService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/products/{productId}/images")
public class ProductImageController {

    private final ProductImageService productImageService;

    public ProductImageController(ProductImageService productImageService) {
        this.productImageService = productImageService;
    }

    /* ===================== ADD IMAGE ===================== */

    @PostMapping
    public ProductImageDTO addImage(
            @PathVariable UUID productId,
            @RequestBody @Valid ProductImageRequestDTO dto
    ) throws BusinessException {

        return productImageService.addImage(productId, dto);
    }

    /* ===================== LIST IMAGES ===================== */

    @GetMapping
    public List<ProductImageDTO> list(
            @PathVariable UUID productId
    ) {
        return productImageService.listByProduct(productId);
    }

    /* ===================== SET MAIN IMAGE ===================== */

    @PatchMapping("/{imageId}/main")
    public void setMainImage(
            @PathVariable UUID productId,
            @PathVariable UUID imageId
    ) throws BusinessException {

        productImageService.setMainImage(imageId);
    }

    /* ===================== DELETE IMAGE ===================== */

    @DeleteMapping("/{imageId}")
    public void delete(
            @PathVariable UUID productId,
            @PathVariable UUID imageId
    ) throws BusinessException {

        productImageService.delete(imageId);
    }
}
