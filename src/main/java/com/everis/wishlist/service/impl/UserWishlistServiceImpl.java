package com.everis.wishlist.service.impl;

import com.everis.wishlist.dto.request.CreateUserWishlistRequest;
import com.everis.wishlist.dto.response.UserWishlistDetailResponse;
import com.everis.wishlist.dto.response.UserWishlistsResponse;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.entity.WishlistDetail;
import com.everis.wishlist.exceptions.UserWishlistNotFoundException;
import com.everis.wishlist.exceptions.http.BadRequestException;
import com.everis.wishlist.exceptions.http.InternalServerException;
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

    private static final String MSG_SOMETHING_WENT_WRONG = "Something went wrong";

    private final WishlistMapper wishlistMapper;
    private final UserWishlistValidator userWishlistValidator;
    private final UserWishlistRepository userWishlistRepository;

    @Transactional
    @Override
    public void createUserWishlist(final UUID userId, final UUID wishlistId, final CreateUserWishlistRequest body) {

        checkCreateUserWishlistRequest(userId, body);

        try {
            log.info("Creating wishlist for user ({})", userId);

            userWishlistRepository.createWishlist(userId, wishlistId, body.getName());
            body.getProductIds().forEach(productId -> userWishlistRepository.createWishlistProduct(wishlistId, productId));

            log.info("User wishlist with id ({}) created successfully", wishlistId);

        } catch (final DuplicateKeyException e) {
            throw new BadRequestException("Name (%s) for wishlist already exists", body.getName());
        } catch (final Exception e) {
            throw new InternalServerException(MSG_SOMETHING_WENT_WRONG);
        }
    }

    @Transactional
    @Override
    public void createUserWishlistProduct(final UUID userId, final UUID wishlistId, final Long productId) {

        checkCanInsertProductOnWishlist(userId, wishlistId, productId);

        try {
            log.info("Creating wishlist ({}) for user ({})", wishlistId, userId);
            userWishlistRepository.createWishlistProduct(wishlistId, productId);
            log.info("Created product with id ({}) for wishlist ({})", productId, wishlistId);
        } catch (final Exception e) {
            throw new InternalServerException("Something went wrong");
        }
    }

    @Transactional
    @Override
    public void deleteUserWishlist(final UUID userId, final UUID wishlistId) {

        checkWishlistOwner(userId, wishlistId);

        try {
            deleteWishlist(userId, wishlistId);
        } catch (final Exception e) {
            throw new InternalServerException("Something went wrong");
        }
    }

    @Transactional
    @Override
    public void deleteUserWishlistProduct(final UUID userId, final UUID wishlistId, final Long productId) {

        checkWishlistOwner(userId, wishlistId);

        try {

            deleteWishlistProduct(userId, wishlistId, productId);

            final WishlistDetail wishlistDetail = findUserWishlist(userId, wishlistId).getWishlistDetail();

            if (wishlistDetail.getProducts().size() == 0) {
                deleteWishlist(userId, wishlistId);
            }

        } catch (final Exception e) {
            throw new InternalServerException("Something went wrong");
        }
    }

    @Override
    public UserWishlistDetailResponse findUserWishlist(final UUID userId, final UUID wishlistId) {

        log.info("Finding user wishlist with userId ({}) and wishlistId ({})", userId, wishlistId);

        try {
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

        log.info("Finding user wishlists with userId ({})", userId);

        try {
            final List<Wishlist> wishlists = userWishlistRepository.findUserWishlists(userId);
            log.info("Found a total of ({}) wishlists for userId ({})", wishlists.size(), userId);

            return UserWishlistsResponse.builder()
                    .wishlists(wishlists)
                    .build();

        } catch (final Exception e) {
            throw new InternalServerException("Something went wrong");
        }
    }

    private void checkCreateUserWishlistRequest(UUID userId, CreateUserWishlistRequest body) {
        final List<Wishlist> wishlists = findUserWishlists(userId).getWishlists();
        userWishlistValidator.validateCreateWishlistRequest(userId, body, wishlists);
    }

    private void checkCanInsertProductOnWishlist(UUID userId, UUID wishlistId, Long productId) {
        final WishlistDetail wishlist = findUserWishlist(userId, wishlistId).getWishlistDetail();
        userWishlistValidator.validateCanInsertProductOnWishlist(wishlist, productId);
    }

    private void checkWishlistOwner(UUID userId, UUID wishlistId) {
        final List<Wishlist> wishlists = findUserWishlists(userId).getWishlists();
        userWishlistValidator.validateWishlistOwner(userId, wishlistId, wishlists);
    }

    private void deleteWishlist(UUID userId, UUID wishlistId) {
        log.info("Deleting wishlist ({}) for user ({})", wishlistId, userId);
        userWishlistRepository.deleteUserWishlist(wishlistId);
        log.info("Deleted successfully wishlist ({}) for user ({})", wishlistId, userId);
    }

    private void deleteWishlistProduct(UUID userId, UUID wishlistId, Long productId) {
        log.info("Deleting product ({}) from wishlist ({}) for user ({})", productId, wishlistId, userId);
        userWishlistRepository.deleteUserWishlistProduct(wishlistId, productId);
        log.info("Deleted successfully product ({}) from wishlist ({}) for user ({})", productId, wishlistId, userId);
    }
}
