package com.everis.wishlist.repository;

import com.everis.wishlist.entity.Wishlist;

import java.util.UUID;

public interface UserWishlistRepository {

    Wishlist findUserWishlist(UUID userId, UUID wishlistId);
}
