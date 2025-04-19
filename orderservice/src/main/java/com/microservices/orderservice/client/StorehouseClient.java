package com.microservices.orderservice.client;

import com.microservices.orderservice.model.RequestProductDTO;
import com.microservices.orderservice.model.ResponseProductDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "storehouse", url = "http://storehouse:8084")
public interface StorehouseClient {

    @PostMapping("/api/v1/products/check")
    List<ResponseProductDTO> checkProduct(@RequestBody List<RequestProductDTO> products);
}
