package com.microservices.storehouse.service;

import com.microservices.storehouse.exceptionHandling.ProductNotFoundException;
import com.microservices.storehouse.model.Product;
import com.microservices.storehouse.model.RequestProductDTO;
import com.microservices.storehouse.model.ResponseProductDTO;
import com.microservices.storehouse.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public List<ResponseProductDTO> checkAndConvertToDTO
            (List<RequestProductDTO> requestProducts) throws ProductNotFoundException {

        List<ResponseProductDTO> responseProductDTOs = new ArrayList<>();
        for (RequestProductDTO requestProductDTO : requestProducts) {
            Optional<Product> product = productRepository
                    .findByNameAndQuantityGreaterThanEqual(
                            requestProductDTO.getName(),
                            requestProductDTO.getQuantity()
                    );

            if (product.isPresent()) {
                product.get().setQuantity(product.get().getQuantity() - requestProductDTO.getQuantity());

                ResponseProductDTO responseProductDTO = new ResponseProductDTO();
                responseProductDTO.setName(product.get().getName());
                responseProductDTO.setPrice(product.get().getPrice());

                responseProductDTOs.add(responseProductDTO);
            } else {
                throw new ProductNotFoundException(requestProductDTO.getName());
            }
        }
        return responseProductDTOs;
    }
}
