package com.everis.wishlist.service;

import com.everis.wishlist.dto.request.CreateUserWishlistRequest;
import com.everis.wishlist.dto.response.UserWishlistDetailResponse;
import com.everis.wishlist.dto.response.UserWishlistsResponse;

import java.util.UUID;

public interface UserWishlistService {
    void createUserWishlist(UUID userId, UUID wishlistId, CreateUserWishlistRequest body);
    void createUserWishlistProduct(UUID userId, UUID wishlistId, Long productId);
    void deleteUserWishlist(UUID userId, UUID wishlistId);
    void deleteUserWishlistProduct(UUID userId, UUID wishlistId, Long productId);
    UserWishlistDetailResponse findUserWishlist(UUID userId, UUID wishlistId);
    UserWishlistsResponse findUserWishlists(UUID userId);
}
