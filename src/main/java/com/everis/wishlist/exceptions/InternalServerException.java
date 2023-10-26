package com.everis.wishlist.exceptions;

import static java.lang.String.format;

public class InternalServerException extends RuntimeException {
    public InternalServerException(final String message, final Object... args) {
        super(format(message, args));
    }
}
