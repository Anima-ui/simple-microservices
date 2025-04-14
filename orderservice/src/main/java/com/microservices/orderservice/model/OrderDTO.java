package com.microservices.orderservice.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;

    private String customerName;

    private List<ProductDTO> products;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String customerEmail;
}
