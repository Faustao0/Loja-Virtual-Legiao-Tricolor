package com.legiaotricolor.BackLegiaoTricolor.service;

import com.legiaotricolor.BackLegiaoTricolor.domain.Product;
import com.legiaotricolor.BackLegiaoTricolor.domain.ProductImage;
import com.legiaotricolor.BackLegiaoTricolor.dto.ProductImageDTO;
import com.legiaotricolor.BackLegiaoTricolor.dto.ProductImageRequestDTO;
import com.legiaotricolor.BackLegiaoTricolor.exception.BusinessException;
import com.legiaotricolor.BackLegiaoTricolor.mapper.ProductImageMapper;
import com.legiaotricolor.BackLegiaoTricolor.repository.ProductImageRepository;
import com.legiaotricolor.BackLegiaoTricolor.repository.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;

    public ProductImageService(ProductImageRepository productImageRepository,
                               ProductRepository productRepository) {
        this.productImageRepository = productImageRepository;
        this.productRepository = productRepository;
    }

    /* ===================== ADD IMAGE ===================== */

    @Transactional
    public ProductImageDTO addImage(
            UUID productId,
            ProductImageRequestDTO dto
    ) throws BusinessException {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new BusinessException("Produto não encontrado"));

        if (Boolean.TRUE.equals(dto.mainImage())) {
            removeMainImage(productId);
        }

        ProductImage image = new ProductImage();
        image.setUrl(dto.url());
        image.setMainImage(dto.mainImage() != null && dto.mainImage());
        image.setProduct(product);

        productImageRepository.save(image);

        return ProductImageMapper.toDTO(image);
    }

    /* ===================== LIST IMAGES ===================== */

    public List<ProductImageDTO> listByProduct(UUID productId) {

        return productImageRepository.findByProductId(productId)
                .stream()
                .map(ProductImageMapper::toDTO)
                .toList();
    }

    /* ===================== SET MAIN IMAGE ===================== */

    @Transactional
    public void setMainImage(UUID imageId) throws BusinessException {

        ProductImage image = productImageRepository.findById(imageId)
                .orElseThrow(() -> new BusinessException("Imagem não encontrada"));

        removeMainImage(image.getProduct().getId());

        image.setMainImage(true);
        productImageRepository.save(image);
    }

    /* ===================== DELETE IMAGE ===================== */

    @Transactional
    public void delete(UUID imageId) throws BusinessException {

        ProductImage image = productImageRepository.findById(imageId)
                .orElseThrow(() -> new BusinessException("Imagem não encontrada"));

        productImageRepository.delete(image);
    }

    /* ===================== PRIVATE ===================== */

    private void removeMainImage(UUID productId) {

        productImageRepository.findByProductId(productId)
                .stream()
                .filter(ProductImage::getMainImage)
                .forEach(image -> {
                    image.setMainImage(false);
                    productImageRepository.save(image);
                });
    }
}
