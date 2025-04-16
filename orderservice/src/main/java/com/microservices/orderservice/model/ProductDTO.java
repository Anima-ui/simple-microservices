package com.microservices.orderservice.model;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;
    private int quantity;

    public static ProductDTO fromEntity(Product product) {
        return new ProductDTO(product.getProductId(), product.getProductName());
    }

    public static Product toEntity(ProductDTO dto) {
        return new Product(dto.getProductId(), dto.getProductName());
    }
}
