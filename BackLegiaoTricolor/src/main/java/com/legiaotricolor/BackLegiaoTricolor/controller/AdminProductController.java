package com.legiaotricolor.BackLegiaoTricolor.controller;

import com.legiaotricolor.BackLegiaoTricolor.dto.ProductRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.ProductResponseDTO;
import com.legiaotricolor.BackLegiaoTricolor.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/admin/products")
@RequiredArgsConstructor
public class AdminProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponseDTO create(
            @Valid @RequestBody ProductRequestDTO dto) {
        return productService.create(dto);
    }

    @PutMapping("/{id}")
    public ProductResponseDTO update(
            @PathVariable UUID id,
            @Valid @RequestBody ProductRequestDTO dto) {
        return productService.update(id, dto);
    }

    @PatchMapping("/{id}/stock")
    public void updateStock(
            @PathVariable UUID id,
            @RequestParam Integer quantity) {
        productService.updateStock(id, quantity);
    }

    @PatchMapping("/{id}/status")
    public void updateStatus(
            @PathVariable UUID id,
            @RequestParam Boolean active) {
        productService.updateStatus(id, active);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        productService.delete(id);
    }
}
