package com.everis.wishlist.controller.handler;

import com.everis.wishlist.exceptions.http.InternalServerException;
import com.everis.wishlist.exceptions.UserWishlistNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserWishlistHandlerController {

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<String> handleInternalServerException(final InternalServerException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserWishlistNotFoundException.class)
    public ResponseEntity<String> handleUserWishlistNotFoundException(final UserWishlistNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }
}
