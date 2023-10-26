package com.everis.wishlist.service;

import com.everis.wishlist.dto.response.UserWishlistResponse;

import java.util.UUID;

public interface WishlistService {
    UserWishlistResponse findUserWishlist(UUID userId, UUID wishlistId);
}
