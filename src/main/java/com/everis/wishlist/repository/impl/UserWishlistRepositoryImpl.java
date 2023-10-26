package com.everis.wishlist.repository.impl;

import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.repository.UserWishlistRepository;

import java.util.UUID;

public class UserWishlistRepositoryImpl implements UserWishlistRepository {

    @Override
    public Wishlist findUserWishList(final UUID userId, final UUID wishlistId) {
        return null;
    }
}
