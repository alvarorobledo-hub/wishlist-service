package com.everis.wishlist.exceptions;

import static java.lang.String.format;

public class UserWishlistNotFound extends RuntimeException {

    public UserWishlistNotFound(final String message, final Object... args) {
        super(format(message, args));
    }
}
