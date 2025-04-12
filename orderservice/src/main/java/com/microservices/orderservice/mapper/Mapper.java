package com.microservices.orderservice.mapper;

import com.microservices.orderservice.model.Order;
import com.microservices.orderservice.model.OrderDTO;
import com.microservices.orderservice.model.ProductDTO;
import org.springframework.stereotype.Component;

@Component
public class Mapper {

    public OrderDTO toOrderDto(Order order) {
        return OrderDTO.builder()
                .orderId(order.getOrderId())
                .customerEmail(order.getCustomerEmail())
                .customerName(order.getCustomerName())
                .products(order.getProducts().stream().map(ProductDTO::fromEntity).toList())
                .build();
    }

    public Order toOrder(OrderDTO dto) {
        return Order.builder()
                .orderId(dto.getOrderId())
                .customerEmail(dto.getCustomerEmail())
                .customerName(dto.getCustomerName())
                .products(dto.getProducts().stream().map(ProductDTO::toEntity).toList())
                .build();
    }
}
