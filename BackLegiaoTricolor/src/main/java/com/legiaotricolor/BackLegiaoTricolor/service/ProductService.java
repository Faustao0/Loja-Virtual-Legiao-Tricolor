package com.legiaotricolor.BackLegiaoTricolor.service;

import com.legiaotricolor.BackLegiaoTricolor.domain.Category;
import com.legiaotricolor.BackLegiaoTricolor.domain.Product;
import com.legiaotricolor.BackLegiaoTricolor.dto.ProductRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.ProductResponseDTO;
import com.legiaotricolor.BackLegiaoTricolor.exception.BusinessException;
import com.legiaotricolor.BackLegiaoTricolor.mapper.ProductMapper;
import com.legiaotricolor.BackLegiaoTricolor.repository.CategoryRepository;
import com.legiaotricolor.BackLegiaoTricolor.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductResponseDTO create(ProductRequestDTO dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new BusinessException("Categoria não encontrada"));

        Product product = Product.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .price(dto.getPrice())
                .stockQuantity(dto.getStockQuantity())
                .category(category)
                .active(true)
                .build();

        return ProductMapper.toDTO(
                productRepository.save(product)
        );
    }

    public ProductResponseDTO update(UUID id, ProductRequestDTO dto) {

        Product product = findEntity(id);

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new BusinessException("Categoria não encontrada"));

        product.setName(dto.getName());
        product.setDescription(dto.getDescription());
        product.setPrice(dto.getPrice());
        product.setCategory(category);

        return ProductMapper.toDTO(
                productRepository.save(product)
        );
    }

    public void updateStock(UUID id, Integer quantity) {
        Product product = findEntity(id);
        product.setStockQuantity(quantity);
        productRepository.save(product);
    }

    public void updateStatus(UUID id, Boolean active) {
        Product product = findEntity(id);
        product.setActive(active);
        productRepository.save(product);
    }

    public void delete(UUID id) {
        Product product = findEntity(id);
        product.setActive(false);
        productRepository.save(product);
    }

    public List<ProductResponseDTO> listActive() {
        return productRepository.findByActiveTrue()
                .stream()
                .map(ProductMapper::toDTO)
                .toList();
    }

    public ProductResponseDTO findById(UUID id) {
        Product product = findEntity(id);

        if (!product.getActive()) {
            throw new BusinessException("Produto indisponível");
        }

        return ProductMapper.toDTO(product);
    }

    private Product findEntity(UUID id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));
    }
}
