package com.everis.wishlist.service.impl;

import com.everis.wishlist.dto.response.UserWishlistDetailResponse;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.entity.WishlistDetail;
import com.everis.wishlist.exceptions.InternalServerException;
import com.everis.wishlist.mapper.WishlistMapper;
import com.everis.wishlist.repository.UserWishlistRepository;
import com.everis.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistMapper wishlistMapper;
    private final UserWishlistRepository userWishlistRepository;

    @Override
    public UserWishlistDetailResponse findUserWishlist(final UUID userId, final UUID wishlistId) {
        try {

            final Wishlist wishlist = userWishlistRepository.findUserWishlist(userId, wishlistId);
            final WishlistDetail wishlistDetail = wishlistMapper.from(wishlist);

            return UserWishlistDetailResponse.builder()
                    .wishlistDetail(wishlistDetail)
                    .build();

        } catch (final Exception e) {
            throw new InternalServerException("Something went wrong");
        }
    }
}
