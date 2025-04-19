package com.microservices.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseOrderDTO {

    private BigDecimal totalPrice;

    private List<ResponseProductDTO> products;

    private String customerName;
}
