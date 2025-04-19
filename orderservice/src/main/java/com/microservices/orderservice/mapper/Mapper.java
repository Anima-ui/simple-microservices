package com.microservices.orderservice.mapper;

import com.microservices.orderservice.model.*;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class Mapper {

    public ResponseOrderDTO orderToResponse(Order order){
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();
        responseOrderDTO.setCustomerName(order.getCustomerName());

        BigDecimal totalPrice = BigDecimal.ZERO;
        if (order.getProducts() != null) {
            for (ResponseProductDTO productDTO : order.getProducts()) {
                totalPrice = totalPrice.add(productDTO.getPrice().multiply(BigDecimal.valueOf(productDTO.getQuantity())));
            }
        }
        responseOrderDTO.setTotalPrice(totalPrice);

        List<ResponseProductDTO> products = order.getProducts() != null
                ? List.copyOf(order.getProducts())
                : List.of();
        responseOrderDTO.setProducts(products);

        return responseOrderDTO;
    }

    public Order requestToOrder(RequestOrderDTO orderDTO, List<ResponseProductDTO> responseProductDTOS) {
        return Order.builder()
                .customerName(orderDTO.getCustomerName())
                .customerEmail(orderDTO.getCustomerEmail())
                .products(responseProductDTOS.stream().map(Mapper::copyOfResponseProduct).toList())
                .build();
    }

    public static ResponseProductDTO copyOfResponseProduct(ResponseProductDTO dto){
        return new ResponseProductDTO(dto.getName(), dto.getPrice(), dto.getQuantity());
    }
}
