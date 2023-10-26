package com.everis.wishlist.repository.impl;

import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.repository.UserWishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserWishlistRepositoryImpl implements UserWishlistRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Wishlist findUserWishList(final UUID userId, final UUID wishlistId) {
        return null;
    }
}
