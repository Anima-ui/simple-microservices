package com.microservices.storehouse.mapper;

import com.microservices.storehouse.model.Product;
import com.microservices.storehouse.model.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ProductDTO toDto(Product entity) {
        return ProductDTO.builder()
                .name(entity.getName())
                .price(entity.getPrice())
                .build();
    }

}
