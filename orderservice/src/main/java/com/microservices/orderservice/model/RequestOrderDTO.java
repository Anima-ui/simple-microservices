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
public class RequestOrderDTO {

    private String customerName;

    private List<RequestProductDTO> requestProducts;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    private String customerEmail;
}
