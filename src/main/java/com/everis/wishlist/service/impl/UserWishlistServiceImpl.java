package com.everis.wishlist.service.impl;

import com.everis.wishlist.dto.response.UserWishlistDetailResponse;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.entity.WishlistDetail;
import com.everis.wishlist.exceptions.InternalServerException;
import com.everis.wishlist.exceptions.UserWishlistNotFoundException;
import com.everis.wishlist.mapper.WishlistMapper;
import com.everis.wishlist.repository.UserWishlistRepository;
import com.everis.wishlist.service.UserWishlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserWishlistServiceImpl implements UserWishlistService {

    private final WishlistMapper wishlistMapper;
    private final UserWishlistRepository userWishlistRepository;

    @Override
    public UserWishlistDetailResponse findUserWishlist(final UUID userId, final UUID wishlistId) {
        try {
            log.info("Finding user wishlist with userId {} and wishlistId {}", userId, wishlistId);

            final Wishlist wishlist = userWishlistRepository.findUserWishlist(userId, wishlistId);
            final WishlistDetail wishlistDetail = wishlistMapper.from(wishlist);

            log.info("Found user wishlist with wishlistId {} and name {}", wishlistId, wishlist.getName());

            return UserWishlistDetailResponse.builder()
                    .wishlistDetail(wishlistDetail)
                    .build();

        } catch (final EmptyResultDataAccessException e) {
            throw new UserWishlistNotFoundException("Wishlist with userId (%s) and wishlistId (%s) does not exists", userId, wishlistId);
        } catch (final Exception e) {
            throw new InternalServerException("Something went wrong");
        }
    }
}
