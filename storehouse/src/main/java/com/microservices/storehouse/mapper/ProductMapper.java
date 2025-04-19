package com.microservices.storehouse.mapper;

import com.microservices.storehouse.model.Product;
import com.microservices.storehouse.model.ResponseProductDTO;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public ResponseProductDTO toDto(Product entity) {
        return ResponseProductDTO.builder()
                .name(entity.getName())
                .price(entity.getPrice())
                .build();
    }

}
