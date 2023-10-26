package com.everis.wishlist.exceptions.http;

import lombok.extern.slf4j.Slf4j;

import static java.lang.String.format;

@Slf4j
public class InternalServerException extends RuntimeException {
    public InternalServerException(final String message, final Object... args) {
        super(format(message, args));
        log.error(format(message, args));
    }
}
