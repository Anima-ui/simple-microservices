package com.microservices.orderservice.repository;

import com.microservices.orderservice.model.Order;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> getOrderByCustomerEmail(@NotBlank(message = "Email is required")
                                  @Email(message = "Invalid email address")
                                  String customerEmail);
}
