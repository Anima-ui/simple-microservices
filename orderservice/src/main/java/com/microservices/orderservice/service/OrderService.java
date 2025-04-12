package com.microservices.orderservice.service;

import com.microservices.orderservice.exceptionHandling.OrderNotFoundException;
import com.microservices.orderservice.mapper.Mapper;
import com.microservices.orderservice.model.Order;
import com.microservices.orderservice.model.OrderDTO;
import com.microservices.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final Mapper mapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void saveOrderDTO(OrderDTO order) {
        try {
            Order savedOrder = orderRepository.save(mapper.toOrder(order));
            order.setOrderId(savedOrder.getOrderId());
        }catch (DataAccessException e) {
            log.error("Failed to save order to database: {}", e.getMessage());
            throw e;
        }

        try {
            kafkaTemplate.send("order-service", order.getCustomerEmail());
        }catch (KafkaException e) {
            log.error("Failed to send message to Kafka for order with email {}: {}", order.getCustomerEmail(), e.getMessage());
            throw new KafkaException("Failed to send message to Kafka " + e.getMessage());
        }
    }

    public OrderDTO getOrderByEmail(String customerEmail) {
        Optional<Order> order = orderRepository.getOrderByCustomerEmail(customerEmail);
        if (order.isPresent()){
            return mapper.toOrderDto(order.get());
        }
        throw new OrderNotFoundException("Order not found for email: " + customerEmail);
    }
}
