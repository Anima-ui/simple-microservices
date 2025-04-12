package com.microservices.orderservice.controller;

import com.microservices.orderservice.client.UserClient;
import com.microservices.orderservice.exceptionHandling.OrderNotFoundException;
import com.microservices.orderservice.model.OrderDTO;
import com.microservices.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/order")
@RequiredArgsConstructor
public class Controller {

    private final OrderService orderService;
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@RequestBody OrderDTO order) {
        if (userClient.userExistsByEmail(order.getCustomerEmail())) {
            orderService.saveOrderDTO(order);
            return new ResponseEntity<>(order, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(order, HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    @Validated
    public ResponseEntity<OrderDTO> getOrder(@RequestParam String customerEmail) {
        if (userClient.userExistsByEmail(customerEmail)) {
            OrderDTO orderDTO = orderService.getOrderByEmail(customerEmail);
            return new ResponseEntity<>(orderDTO, HttpStatus.OK);
        } else {
            throw new OrderNotFoundException("Order not found for email: " + customerEmail);
        }
    }
}
