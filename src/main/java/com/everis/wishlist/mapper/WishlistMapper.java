package com.everis.wishlist.mapper;

import com.everis.wishlist.client.ProductApiClient;
import com.everis.wishlist.entity.Product;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.entity.WishlistDetail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.everis.wishlist.utils.ObjectMapperHelper.getObject;

@Component
@RequiredArgsConstructor
public class WishlistMapper {

    private final ProductApiClient productApiClient;

    public WishlistDetail from(final Wishlist wishlist) throws JsonProcessingException {
        List<Product> products = new ArrayList<>();

        for (Long productId : wishlist.getProductIds()) {
            final Product product = getObject(productApiClient.getProduct(productId), new TypeReference<Product>() {});
            products.add(product);
        }

        return WishlistDetail.builder()
                .name(wishlist.getName())
                .productIds(products)
                .build();
    }
}
