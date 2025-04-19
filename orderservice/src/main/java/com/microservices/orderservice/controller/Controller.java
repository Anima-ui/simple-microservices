package com.microservices.orderservice.controller;

import com.microservices.orderservice.client.UserClient;
import com.microservices.orderservice.exceptionHandling.UserNotFoundException;
import com.microservices.orderservice.model.RequestOrderDTO;
import com.microservices.orderservice.model.ResponseOrderDTO;
import com.microservices.orderservice.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/order")
@RequiredArgsConstructor
@Validated
public class Controller {

    private final OrderService orderService;
    private final UserClient userClient;

    @PostMapping
    public ResponseEntity<ResponseOrderDTO> createOrder(@RequestBody @Valid RequestOrderDTO order) {
        if (userClient.userExistsByEmail(order.getCustomerEmail())) {
            ResponseOrderDTO responseOrderDTO = orderService.saveRequestOrderDTO(order);
            return new ResponseEntity<>(responseOrderDTO, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping
    public ResponseEntity<ResponseOrderDTO> getOrder(@RequestParam
                                                 @Email(message = "Invalid Email")
                                                 @NotBlank(message = "Email is required")
                                                 String customerEmail) {
        if (userClient.userExistsByEmail(customerEmail)) {
            ResponseOrderDTO orderDTO = orderService.getOrderByEmail(customerEmail);
            return new ResponseEntity<>(orderDTO, HttpStatus.OK);
        } else {
            throw new UserNotFoundException("User not found for email: " + customerEmail);
        }
    }
}
