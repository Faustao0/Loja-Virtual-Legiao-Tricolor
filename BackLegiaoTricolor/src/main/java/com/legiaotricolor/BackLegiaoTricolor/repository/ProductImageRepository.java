package com.legiaotricolor.BackLegiaoTricolor.repository;

import com.legiaotricolor.BackLegiaoTricolor.domain.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductImageRepository
        extends JpaRepository<ProductImage, UUID> {

    List<ProductImage> findByProductId(UUID productId);

    boolean existsByProductIdAndMainImageTrue(UUID productId);
}
