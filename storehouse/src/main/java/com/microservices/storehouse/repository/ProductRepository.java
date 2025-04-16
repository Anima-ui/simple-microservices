package com.microservices.storehouse.repository;

import com.microservices.storehouse.model.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product, Long> {
}
