package com.microservices.orderservice.model;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RequestProductDTO {

    @NotBlank(message = "Name is required")
    private String name;

    @Column(nullable = false)
    @Min(value = 0, message = "Quantity cannot be negative")
    private int quantity;
}
