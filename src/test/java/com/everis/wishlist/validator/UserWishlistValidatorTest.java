package com.everis.wishlist.validator;

import com.everis.wishlist.dto.request.CreateUserWishlistRequest;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.exceptions.MaxWishlistsPerUserException;
import com.everis.wishlist.exceptions.http.BadRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static com.everis.wishlist.constants.WishlistServiceConstants.USER_ID;
import static com.everis.wishlist.mock.WishlistServiceMock.getCreateUserWishlistRequest;
import static com.everis.wishlist.mock.WishlistServiceMock.getWishlists;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserWishlistValidatorTest {

    @InjectMocks
    private UserWishlistValidator userWishlistValidator;

    @Test
    void should_request_is_ok_on_validate_create_user_wishlist() throws JsonProcessingException {

        final CreateUserWishlistRequest request = getCreateUserWishlistRequest();
        final List<Wishlist> wishlists = getWishlists();

        // WHEN
        userWishlistValidator.validate(USER_ID, request, wishlists);
    }

    @Test
    void should_request_throw_error_if_name_is_null_or_empty_on_validate_create_user_wishlist() throws JsonProcessingException {
        final CreateUserWishlistRequest request = getCreateUserWishlistRequest();
        final List<Wishlist> wishlists = getWishlists();

        // GIVEN
        request.setName(null);

        // WHEN
        final BadRequestException exception = assertThrows(BadRequestException.class,
                () -> userWishlistValidator.validate(USER_ID, request, wishlists));

        // THEN
        assertAll("Exception should be:",
                () -> assertEquals("Wishlist name must not be void", exception.getMessage()));
    }

    @Test
    void should_request_throw_error_if_product_ids_is_empty_on_validate_create_user_wishlist() throws JsonProcessingException {
        final CreateUserWishlistRequest request = getCreateUserWishlistRequest();
        final List<Wishlist> wishlists = getWishlists();

        // GIVEN
        request.setProductIds(Collections.emptyList());

        // WHEN
        final BadRequestException exception = assertThrows(BadRequestException.class,
                () -> userWishlistValidator.validate(USER_ID, request, wishlists));

        // THEN
        assertAll("Exception should be:",
                () -> assertEquals("ProductIds must have minimum one product", exception.getMessage()));
    }

    @Test
    void should_request_throw_error_if_wishlist_size_is_five_or_more_on_validate_create_user_wishlist() throws JsonProcessingException {
        final CreateUserWishlistRequest request = getCreateUserWishlistRequest();
        final List<Wishlist> wishlists = getWishlists();

        // GIVEN
        wishlists.add(Wishlist.builder().name("test-4").productIds(Collections.singletonList(1L)).build());
        wishlists.add(Wishlist.builder().name("test-5").productIds(Collections.singletonList(2L)).build());

        // WHEN
        final MaxWishlistsPerUserException exception = assertThrows(MaxWishlistsPerUserException.class,
                () -> userWishlistValidator.validate(USER_ID, request, wishlists));

        // THEN
        assertAll("Exception should be:",
                () -> assertEquals(format("User %s actually have %s wishlists. Cannot create more", USER_ID, 5), exception.getMessage()));
    }

}
