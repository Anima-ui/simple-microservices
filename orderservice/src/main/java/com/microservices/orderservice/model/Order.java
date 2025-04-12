package com.microservices.orderservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "Orders",
        uniqueConstraints = @UniqueConstraint(columnNames = "customerEmail"))
@Builder
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderId;

    @NotBlank(message = "Name is required")
    private String customerName;

    @ManyToMany
    @JoinTable(name = "order_products",
               joinColumns = @JoinColumn(name = "order_id"),
               inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String customerEmail;
}
