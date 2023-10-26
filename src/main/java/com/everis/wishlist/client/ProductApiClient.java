package com.everis.wishlist.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-api-client", url = "${urls.product-api-client}")
public interface ProductApiClient {

    @GetMapping(value = "/product/{productId}")
    String getProduct(@PathVariable Long productId);
}
