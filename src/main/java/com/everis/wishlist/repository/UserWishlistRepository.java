package com.everis.wishlist.repository;

import com.everis.wishlist.entity.Wishlist;

import java.util.UUID;

public interface UserWishlistRepository {

    Wishlist findUserWishList(UUID userId, UUID wishlistId);
}
