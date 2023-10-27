package com.everis.wishlist.mapper;

import com.everis.wishlist.client.ProductApiClient;
import com.everis.wishlist.entity.Product;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.entity.WishlistDetail;
import com.everis.wishlist.repository.UserWishlistRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import feign.FeignException.FeignClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.everis.wishlist.utils.ObjectMapperHelper.getObject;

@Component
@RequiredArgsConstructor
public class WishlistMapper {

    private final UserWishlistRepository userWishlistRepository;
    private final ProductApiClient productApiClient;

    public WishlistDetail from(final Wishlist wishlist) throws JsonProcessingException {
        final List<Product> products = wishlist.getProductIds().stream()
                .map(productId -> fetchProduct(wishlist, productId))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return WishlistDetail.builder()
                .id(wishlist.getId())
                .name(wishlist.getName())
                .products(products)
                .build();
    }

    private Product fetchProduct(final Wishlist wishlist, final Long productId) {
        try {
            final String response = productApiClient.getProduct(productId);
            return response != null ? getObject(response, new TypeReference<Product>() {}) : null;
        } catch (final FeignClientException.NotFound | JsonProcessingException e) {
            userWishlistRepository.deleteUserWishlistProduct(wishlist.getId(), productId);
            return null;
        }
    }
}
