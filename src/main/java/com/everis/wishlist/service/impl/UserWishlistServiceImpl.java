package com.everis.wishlist.service.impl;

import com.everis.wishlist.dto.request.CreateUserWishlistRequest;
import com.everis.wishlist.dto.request.CreateWishlistProductRequest;
import com.everis.wishlist.dto.response.UserWishlistDetailResponse;
import com.everis.wishlist.dto.response.UserWishlistsResponse;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.entity.WishlistDetail;
import com.everis.wishlist.exceptions.http.BadRequestException;
import com.everis.wishlist.exceptions.http.InternalServerException;
import com.everis.wishlist.exceptions.UserWishlistNotFoundException;
import com.everis.wishlist.mapper.WishlistMapper;
import com.everis.wishlist.repository.UserWishlistRepository;
import com.everis.wishlist.service.UserWishlistService;
import com.everis.wishlist.validator.UserWishlistValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserWishlistServiceImpl implements UserWishlistService {

    private final WishlistMapper wishlistMapper;
    private final UserWishlistValidator userWishlistValidator;
    private final UserWishlistRepository userWishlistRepository;

    @Transactional
    @Override
    public void createUserWishlist(final UUID userId, final UUID wishlistId, final CreateUserWishlistRequest body) {
        final List<Wishlist> wishlists = findUserWishlists(userId).getWishlists();

        userWishlistValidator.validate(userId, body, wishlists);

        try {
            log.info("Creating wishlist for user ({})", userId);

            userWishlistRepository.createWishlist(userId, wishlistId, body.getName());
            for (Long productId : body.getProductIds()) {
                userWishlistRepository.createWishlistProduct(wishlistId, productId);
            }

            log.info("User wishlist with id ({}) created successfully", wishlistId);

        } catch (final DuplicateKeyException e) {
            throw new BadRequestException("Name (%s) for wishlist already exists", body.getName());
        } catch (final Exception e) {
            throw new InternalServerException("Something went wrong");
        }
    }

    @Override
    public void createUserWishlistProduct(final UUID userId, final UUID wishlistId, final CreateWishlistProductRequest body) {
        final WishlistDetail wishlist = findUserWishlist(userId, wishlistId).getWishlistDetail();
        userWishlistValidator.validate(wishlist, body);

        try {
            log.info("Creating wishlist ({}) for user ({})", wishlistId, userId);
            userWishlistRepository.createWishlistProduct(wishlistId, body.getProductId());
            log.info("Created product with id ({}) for wishlist ({})", body.getProductId(), wishlist.getId());
        } catch (final Exception e) {
            throw new InternalServerException("Something went wrong");
        }
    }

    @Override
    public void deleteUserWishlist(final UUID userId, final UUID wishlistId) {

        final List<Wishlist> wishlists = findUserWishlists(userId).getWishlists();
        userWishlistValidator.validateWishlistOwner(userId, wishlistId, wishlists);

        try {
            log.info("Deleting wishlist ({}) for user ({})", wishlistId, userId);
            userWishlistRepository.deleteUserWishlist(wishlistId);
            log.info("Deleted successfully wishlist ({}) for user ({})", wishlistId, userId);
        } catch (final Exception e) {
            throw new InternalServerException("Something went wrong");
        }
    }

    @Override
    public UserWishlistDetailResponse findUserWishlist(final UUID userId, final UUID wishlistId) {
        try {
            log.info("Finding user wishlist with userId ({}) and wishlistId ({})", userId, wishlistId);

            final Wishlist wishlist = userWishlistRepository.findUserWishlist(userId, wishlistId);
            final WishlistDetail wishlistDetail = wishlistMapper.from(wishlist);

            log.info("Found user wishlist with wishlistId ({}) and name ({})", wishlistId, wishlist.getName());

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
            log.info("Finding user wishlists with userId ({})", userId);

            final List<Wishlist> wishlists = userWishlistRepository.findUserWishlists(userId);

            log.info("Found a total of ({}) wishlists for userId ({})", wishlists.size(), userId);

            return UserWishlistsResponse.builder()
                    .wishlists(wishlists)
                    .build();

        } catch (final Exception e) {
            throw new InternalServerException("Something went wrong");
        }
    }
}
