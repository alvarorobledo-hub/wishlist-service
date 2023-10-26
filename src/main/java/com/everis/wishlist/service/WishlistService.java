package com.everis.wishlist.service;

import com.everis.wishlist.dto.response.UserWishlistDetailResponse;

import java.util.UUID;

public interface WishlistService {
    UserWishlistDetailResponse findUserWishlist(UUID userId, UUID wishlistId);
}
