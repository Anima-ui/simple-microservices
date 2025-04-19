package com.microservices.storehouse.repository;

import com.microservices.storehouse.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Product> findByNameAndQuantityGreaterThanEqual(String name, int quantity);
}
