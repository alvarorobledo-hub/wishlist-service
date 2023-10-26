package com.everis.wishlist.exceptions;

import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;

@Slf4j
public class UserWishlistNotFound extends RuntimeException {

    public UserWishlistNotFound(final String message, final Object... args) {
        super(format(message, args));
        log.error(format(message, args));
    }
}
