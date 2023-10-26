package com.everis.wishlist.repository;

import com.everis.wishlist.entity.Wishlist;

import java.util.List;
import java.util.UUID;

public interface UserWishlistRepository {

    UUID createWishlist(UUID userId, String name);
    void createWishlistProduct(UUID wishlistId, Long productId);
    Wishlist findUserWishlist(UUID userId, UUID wishlistId);
    List<Wishlist> findUserWishlists(UUID userId);
}
