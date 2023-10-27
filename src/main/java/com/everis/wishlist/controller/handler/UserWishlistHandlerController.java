package com.everis.wishlist.controller.handler;

import com.everis.wishlist.exceptions.MaxProductsPerWishlistException;
import com.everis.wishlist.exceptions.MaxWishlistsPerUserException;
import com.everis.wishlist.exceptions.http.BadRequestException;
import com.everis.wishlist.exceptions.http.InternalServerException;
import com.everis.wishlist.exceptions.UserWishlistNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserWishlistHandlerController {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(final BadRequestException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<String> handleInternalServerException(final InternalServerException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(UserWishlistNotFoundException.class)
    public ResponseEntity<String> handleUserWishlistNotFoundException(final UserWishlistNotFoundException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MaxWishlistsPerUserException.class)
    public ResponseEntity<String> handleMaxWishlistsPerUserException(final MaxWishlistsPerUserException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(MaxProductsPerWishlistException.class)
    public ResponseEntity<String> handleMaxProductsPerWishlistException(final MaxProductsPerWishlistException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
    }
}
