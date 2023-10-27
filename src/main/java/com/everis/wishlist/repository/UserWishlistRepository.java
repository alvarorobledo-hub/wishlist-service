package com.everis.wishlist.repository;

import com.everis.wishlist.entity.Wishlist;

import java.util.List;
import java.util.UUID;

public interface UserWishlistRepository {

    void createWishlist(UUID userId, UUID wishlistId, String name);
    void createWishlistProduct(UUID wishlistId, Long productId);
    void deleteUserWishlist(UUID userId, UUID wishlistId);
    Wishlist findUserWishlist(UUID userId, UUID wishlistId);
    List<Wishlist> findUserWishlists(UUID userId);
}
