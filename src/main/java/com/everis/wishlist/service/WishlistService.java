package com.everis.wishlist.service;

import com.everis.wishlist.dto.response.UserWishlistResponse;

import java.util.UUID;

public interface WishlistService {
    UserWishlistResponse findUserWishList(UUID userId, UUID wishlistId);
}
