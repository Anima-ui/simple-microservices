package com.microservices.storehouse.controller;

import com.microservices.storehouse.model.RequestProductDTO;
import com.microservices.storehouse.model.ResponseProductDTO;
import com.microservices.storehouse.service.ProductService;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/products")
@Validated
@NoArgsConstructor
public class Controller {

    private ProductService productService;

    @Autowired
    public Controller(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(path = "/check")
    public ResponseEntity<List<ResponseProductDTO>> getProducts(
            @RequestBody List<RequestProductDTO> products) {

        List<ResponseProductDTO> responseProductDTOs = productService.checkAndConvertToDTO(products);
        return new ResponseEntity<>(responseProductDTOs, HttpStatus.OK);
    }
}
