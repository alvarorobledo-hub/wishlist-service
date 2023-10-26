package com.everis.wishlist.service.impl;

import com.everis.wishlist.dto.response.UserWishlistDetailResponse;
import com.everis.wishlist.dto.response.UserWishlistsResponse;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.entity.WishlistDetail;
import com.everis.wishlist.exceptions.InternalServerException;
import com.everis.wishlist.exceptions.UserWishlistNotFoundException;
import com.everis.wishlist.mapper.WishlistMapper;
import com.everis.wishlist.repository.UserWishlistRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static com.everis.wishlist.constants.WishlistServiceConstants.USER_ID;
import static com.everis.wishlist.constants.WishlistServiceConstants.WISHLIST_ID;
import static com.everis.wishlist.mock.WishlistServiceMock.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistServiceImplTest {

    @Mock
    private WishlistMapper wishlistMapper;

    @Mock
    private UserWishlistRepository userWishlistRepository;

    @InjectMocks
    private UserWishlistServiceImpl userWishlistService;

    @Test
    void should_return_a_user_detail_wishlist() throws JsonProcessingException {

        final Wishlist wishlist = getWishlist();
        final WishlistDetail wishlistDetail = getWishlistDetail();

        // GIVEN
        doReturn(wishlist).when(userWishlistRepository).findUserWishlist(USER_ID, WISHLIST_ID);
        doReturn(wishlistDetail).when(wishlistMapper).from(wishlist);

        // WHEN
        final UserWishlistDetailResponse response = userWishlistService.findUserWishlist(USER_ID, WISHLIST_ID);

        // THEN
        assertAll("Response should be:",
                () -> assertEquals(wishlistDetail, response.getWishlistDetail()));
        verify(userWishlistRepository).findUserWishlist(USER_ID, WISHLIST_ID);
        verify(wishlistMapper).from(wishlist);
    }

    @Test
    void should_throw_error_if_userId_and_wishlistId_not_found_on_find_user_wishlist() throws JsonProcessingException {

        // GIVEN
        doThrow(new EmptyResultDataAccessException(0)).when(userWishlistRepository).findUserWishlist(USER_ID, WISHLIST_ID);

        // WHEN
        final UserWishlistNotFoundException exception = assertThrows(UserWishlistNotFoundException.class,
                () -> userWishlistService.findUserWishlist(USER_ID, WISHLIST_ID));

        // THEN
        assertAll("Exception should be:",
                () -> assertEquals(format("Wishlist with userId (%s) and wishlistId (%s) does not exists", USER_ID, WISHLIST_ID), exception.getMessage()));
        verify(userWishlistRepository).findUserWishlist(USER_ID, WISHLIST_ID);
        verify(wishlistMapper, never()).from(any());

    }

    @Test
    void should_throw_error_if_something_went_wrong_on_find_user_wishlist() throws JsonProcessingException {

        final Wishlist wishlist = getWishlist();

        // GIVEN
        doReturn(wishlist).when(userWishlistRepository).findUserWishlist(USER_ID, WISHLIST_ID);
        doThrow(new RuntimeException()).when(wishlistMapper).from(wishlist);

        // WHEN
        final InternalServerException exception = assertThrows(InternalServerException.class,
                () -> userWishlistService.findUserWishlist(USER_ID, WISHLIST_ID));

        // THEN
        assertAll("Exception should be:",
                () -> assertEquals("Something went wrong", exception.getMessage()));
        verify(userWishlistRepository).findUserWishlist(USER_ID, WISHLIST_ID);
        verify(wishlistMapper).from(wishlist);

    }

    @Test
    void should_return_user_wishlists() throws JsonProcessingException {
        final List<Wishlist> wishlists = getWishlists();

        // GIVEN
        doReturn(wishlists).when(userWishlistRepository).findUserWishlists(USER_ID);

        // WHEN
        final UserWishlistsResponse response = userWishlistService.findUserWishlists(USER_ID);

        // THEN
        assertAll("Response should be:",
                () -> assertEquals(wishlists, response.getWishlists()));
        verify(userWishlistRepository).findUserWishlists(USER_ID);
    }

    @Test
    void should_throw_error_if_something_went_wrong_on_find_user_wishlists() {

        // GIVEN
        doThrow(new RuntimeException()).when(userWishlistRepository).findUserWishlists(USER_ID);

        // WHEN
        final InternalServerException exception = assertThrows(InternalServerException.class,
                () -> userWishlistService.findUserWishlists(USER_ID));

        // THEN
        assertAll("Exception should be:",
                () -> assertEquals("Something went wrong", exception.getMessage()));
        verify(userWishlistRepository).findUserWishlists(USER_ID);
    }

}
