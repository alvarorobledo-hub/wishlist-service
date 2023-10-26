package com.everis.wishlist.service.impl;

import com.everis.wishlist.client.ProductApiClient;
import com.everis.wishlist.dto.response.UserWishlistResponse;
import com.everis.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final ProductApiClient productApiClient;
    private final UserWishlistRepository userWishlistRepository;

    @Override
    public UserWishlistResponse findUserWishlist(UUID userId, UUID wishlistId) {
        return null;
    }
}
