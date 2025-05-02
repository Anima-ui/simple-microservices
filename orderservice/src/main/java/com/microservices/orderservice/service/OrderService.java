package com.microservices.orderservice.service;

import com.microservices.orderservice.client.StorehouseClient;
import com.microservices.orderservice.exceptionHandling.OrderNotFoundException;
import com.microservices.orderservice.mapper.Mapper;
import com.microservices.orderservice.model.Order;
import com.microservices.orderservice.model.RequestOrderDTO;
import com.microservices.orderservice.model.ResponseOrderDTO;
import com.microservices.orderservice.model.ResponseProductDTO;
import com.microservices.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final Mapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final StorehouseClient storehouseClient;

    public ResponseOrderDTO saveRequestOrderDTO(RequestOrderDTO order) {
        ResponseOrderDTO responseOrderDTO = new ResponseOrderDTO();

        try {
            List<ResponseProductDTO> responseProductDTOS = storehouseClient.checkProduct(order.getRequestProducts());
            Order savedOrder = orderRepository.save(mapper.requestToOrder(order, responseProductDTOS));
            responseOrderDTO = mapper.orderToResponse(savedOrder);
        }catch (DataAccessException e) {
            log.error("Failed to save order to database: {}", e.getMessage());
        }catch (RuntimeException e) {
            log.error("Something went wrong: {}", e.getMessage());
            throw e;
        }

        try {
            kafkaTemplate.send("order-service", order.getCustomerEmail());
        }catch (KafkaException e) {
            log.error("Failed to send message to Kafka for order with email {}: {}", order.getCustomerEmail(), e.getMessage());
            throw new KafkaException("Failed to send message to Kafka " + e.getMessage());
        }
        return responseOrderDTO;
    }


    public List<ResponseOrderDTO> getOrderByEmail(String customerEmail) {
        List<Order> order = orderRepository.getOrderByCustomerEmail(customerEmail);
        if (!order.isEmpty()) {
            List<ResponseOrderDTO> responseOrderDTOS = new ArrayList<>();
            for (Order orderItem : order) {
                responseOrderDTOS.add(mapper.orderToResponse(orderItem));
            }
            return responseOrderDTOS;
        }
        throw new OrderNotFoundException("Order not found for email: " + customerEmail);
    }
}
