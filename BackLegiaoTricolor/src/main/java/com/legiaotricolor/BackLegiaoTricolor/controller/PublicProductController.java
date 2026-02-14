package com.legiaotricolor.BackLegiaoTricolor.controller;

import com.legiaotricolor.BackLegiaoTricolor.dto.ProductResponseDTO;
import com.legiaotricolor.BackLegiaoTricolor.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class PublicProductController {

    private final ProductService productService;

    @GetMapping
    public List<ProductResponseDTO> list() {
        return productService.listActive();
    }

    @GetMapping("/{id}")
    public ProductResponseDTO detail(@PathVariable UUID id) {
        return productService.findById(id);
    }
}
