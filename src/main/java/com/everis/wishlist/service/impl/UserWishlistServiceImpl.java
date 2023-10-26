package com.everis.wishlist.service.impl;

import com.everis.wishlist.dto.request.CreateUserWishlistRequest;
import com.everis.wishlist.dto.response.UserWishlistDetailResponse;
import com.everis.wishlist.dto.response.UserWishlistsResponse;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.entity.WishlistDetail;
import com.everis.wishlist.exceptions.InternalServerException;
import com.everis.wishlist.exceptions.MaxWishlistsPerUserException;
import com.everis.wishlist.exceptions.UserWishlistNotFoundException;
import com.everis.wishlist.mapper.WishlistMapper;
import com.everis.wishlist.repository.UserWishlistRepository;
import com.everis.wishlist.service.UserWishlistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserWishlistServiceImpl implements UserWishlistService {

    private static final Integer MAX_WISHLISTS_PER_USER = 5;

    private final WishlistMapper wishlistMapper;
    private final UserWishlistRepository userWishlistRepository;

    @Transactional
    @Override
    public void createUserWishlist(final UUID userId, final CreateUserWishlistRequest body) {
        try {

            log.info("Creating wishlist for user ({})", userId);

            if (body.getProductIds().size() == 0) {
                throw new
            }

            final List<Wishlist> wishlists = findUserWishlists(userId).getWishlists();

            if (wishlists.size() >= MAX_WISHLISTS_PER_USER) {
                throw new MaxWishlistsPerUserException("User %s actually have %s wishlists. Can not create more", userId, MAX_WISHLISTS_PER_USER);
            }

            final Wishlist wishlist = userWishlistRepository.createWishlist(userId, body.getName());
            userWishlistRepository.createUserWishlist(userId, wishlist.getId());
            userWishlistRepository.createWishlistProduct(wishlist.getId(), body.getProductIds());

            log.info("User wishlist with id {} created successfully", wishlist.getId());

        } catch (final Exception e) {
            throw new InternalServerException("Something went wrong");
        }
    }

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

    @Override
    public UserWishlistsResponse findUserWishlists(final UUID userId) {
        try {
            log.info("Finding user wishlists with userId {}", userId);

            final List<Wishlist> wishlists = userWishlistRepository.findUserWishlists(userId);

            log.info("Found a total of {} wishlists for userId {}", wishlists.size(), userId);

            return UserWishlistsResponse.builder()
                    .wishlists(wishlists)
                    .build();

        } catch (final Exception e) {
            throw new InternalServerException("Something went wrong");
        }
    }
}
