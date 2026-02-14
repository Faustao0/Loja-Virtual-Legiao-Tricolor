package com.legiaotricolor.BackLegiaoTricolor.repository;

import com.legiaotricolor.BackLegiaoTricolor.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

    List<Product> findByActiveTrue();

    List<Product> findByCategoryId(UUID categoryId);
}
