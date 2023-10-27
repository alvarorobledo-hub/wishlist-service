package com.everis.wishlist.controller.handler;

import com.everis.wishlist.exceptions.MaxProductsPerWishlistException;
import com.everis.wishlist.exceptions.MaxWishlistsPerUserException;
import com.everis.wishlist.exceptions.http.BadRequestException;
import com.everis.wishlist.exceptions.http.InternalServerException;
import com.everis.wishlist.exceptions.UserWishlistNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class UserWishlistHandlerControllerTest {

    @InjectMocks
    private UserWishlistHandlerController userWishlistHandlerController;

    @Test
    void handler_should_throw_bad_request_exception() {

        final String errorMsg = "Bad request message";

        // GIVEN
        final BadRequestException exception = new BadRequestException(errorMsg);

        // WHEN
        final ResponseEntity<String> response = userWishlistHandlerController.handleBadRequestException(exception);

        // THEN
        assertAll("Exception should be:",
                () -> assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode()),
                () -> assertEquals(errorMsg, response.getBody()));
    }

    @Test
    void handler_should_throw_internal_server_exception() {

        final String errorMsg = "Something went wrong";

        // GIVEN
        final InternalServerException exception = new InternalServerException(errorMsg);

        // WHEN
        final ResponseEntity<String> response = userWishlistHandlerController.handleInternalServerException(exception);

        // THEN
        assertAll("Exception should be:",
                () -> assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode()),
                () -> assertEquals(errorMsg, response.getBody()));
    }

    @Test
    void handler_should_throw_user_wishlist_not_found_exception() {
        final String errorMsg = "User wishlist not found";

        // GIVEN
        final UserWishlistNotFoundException exception = new UserWishlistNotFoundException(errorMsg);

        // WHEN
        final ResponseEntity<String> response = userWishlistHandlerController.handleUserWishlistNotFoundException(exception);

        // THEN
        assertAll("Exception should be:",
                () -> assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode()),
                () -> assertEquals(errorMsg, response.getBody()));
    }

    @Test
    void handler_should_throw_max_wishlists_per_user_exception() {
        final String errorMsg = "User 123 actually have 5 wishlists. Cannot create more";

        // GIVEN
        final MaxWishlistsPerUserException exception = new MaxWishlistsPerUserException(errorMsg);

        // WHEN
        final ResponseEntity<String> response = userWishlistHandlerController.handleMaxWishlistsPerUserException(exception);

        // THEN
        assertAll("Exception should be:",
                () -> assertEquals(HttpStatus.CONFLICT, response.getStatusCode()),
                () -> assertEquals(errorMsg, response.getBody()));
    }

    @Test
    void handler_should_throw_max_products_per_wishlist_exception() {
        final String errorMsg = "Wishlist 123 actually have 25 products. Cannot create more";

        // GIVEN
        final MaxProductsPerWishlistException exception = new MaxProductsPerWishlistException(errorMsg);

        // WHEN
        final ResponseEntity<String> response = userWishlistHandlerController.handleMaxProductsPerWishlistException(exception);

        // THEN
        assertAll("Exception should be:",
                () -> assertEquals(HttpStatus.CONFLICT, response.getStatusCode()),
                () -> assertEquals(errorMsg, response.getBody()));
    }

}
