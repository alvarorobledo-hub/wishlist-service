package com.everis.wishlist.mock;

import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.entity.WishlistDetail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import static com.everis.wishlist.utils.FileHelper.load;
import static com.everis.wishlist.utils.ObjectMapperHelper.getObject;

public class WishlistServiceMock {

    public static Wishlist getWishlist() throws JsonProcessingException {
        return getObject(load("/wishlist.json"), new TypeReference<Wishlist>() {});
    }

    public static WishlistDetail getWishlistDetail() throws JsonProcessingException {
        return getObject(load("/wishlist_detail.json"), new TypeReference<WishlistDetail>() {});
    }
}
