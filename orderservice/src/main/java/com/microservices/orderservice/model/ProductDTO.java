package com.microservices.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long productId;
    private String productName;

    public static ProductDTO fromEntity(Product product) {
        return new ProductDTO(product.getProductId(), product.getProductName());
    }

    public static Product toEntity(ProductDTO dto) {
        return new Product(dto.getProductId(), dto.getProductName());
    }
}
