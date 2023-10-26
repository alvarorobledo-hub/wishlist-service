package com.everis.wishlist.controller.handler;

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

}
