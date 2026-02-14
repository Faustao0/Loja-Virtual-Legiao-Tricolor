package com.legiaotricolor.BackLegiaoTricolor.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "product_images")
@Data
public class ProductImage {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String url;

    private Boolean mainImage = false;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
}

