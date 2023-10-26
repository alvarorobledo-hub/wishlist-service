package com.everis.wishlist.constants;

import java.util.UUID;

public class WishlistServiceConstants {

    public static final UUID USER_ID = UUID.fromString("cb548e14-91da-44ca-9361-bde602a3b6ab");
    public static final UUID WISHLIST_ID = UUID.fromString("21b37e55-8d5f-4c0c-a90f-e3e46aa17404");
    public static final String WISHLIST_NAME = "test-4";

    public static final String FILE_CREATE_USER_WISHLIST = "/db/files/create_user_wishlist.sql";
    public static final String FILE_CREATE_WISHLIST = "/db/files/create_wishlist.sql";
    public static final String FILE_CREATE_WISHLIST_PRODUCT = "/db/files/create_wishlist_product.sql";
    public static final String FILE_FIND_USER_WISHLIST = "/db/files/find_user_wishlist.sql";
    public static final String FILE_FIND_USER_WISHLISTS = "/db/files/find_user_wishlists.sql";
}
