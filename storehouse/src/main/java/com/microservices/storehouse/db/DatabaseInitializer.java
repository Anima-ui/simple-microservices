package com.microservices.storehouse.db;

import com.microservices.storehouse.model.Product;
import com.microservices.storehouse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
@Slf4j
public class DatabaseInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;

    @Override
    public void run(String... args){
        try {
            Product product1 = Product.builder()
                    .name("Iphone 16")
                    .price(new BigDecimal("349.99"))
                    .quantity(100)
                    .description("New iphone")
                    .build();

            Product product2 = Product.builder()
                    .name("Iphone 8")
                    .price(new BigDecimal("199.99"))
                    .quantity(34)
                    .description("Not so new iphone")
                    .build();

            productRepository.save(product1);
            productRepository.save(product2);

            log.info("Created products: Iphone 16 and Iphone 8");
        } catch (Exception e) {
            log.error("Error while creating products: {}", e.getMessage(), e);
        }
    }
}
