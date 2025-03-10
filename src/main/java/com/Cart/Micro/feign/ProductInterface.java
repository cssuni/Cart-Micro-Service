package com.Cart.Micro.feign;


import com.Cart.Micro.model.ProductDTOforCart;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name = "productServiceClient", url = "http://localhost:8080/api/v1/products")
public interface ProductInterface {

    @GetMapping("/getProductForCart")
    public ProductDTOforCart getProductDetails(@RequestParam Long productId);
}
