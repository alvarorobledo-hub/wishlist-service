package com.everis.wishlist.mock;

import com.everis.wishlist.entity.Product;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.entity.WishlistDetail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import static com.everis.wishlist.utils.FileHelper.load;
import static com.everis.wishlist.utils.ObjectMapperHelper.getObject;

public class WishlistServiceMock {

    public static String getProduct(final Integer id) {
        return load("/products/product" + id + ".json");
    }

    public static Wishlist getWishlist() throws JsonProcessingException {
        return getObject(load("/wishlist.json"), new TypeReference<Wishlist>() {});
    }

    public static List<Wishlist> getWishlists() throws JsonProcessingException {
        return getObject(load("/wishlists.json"), new TypeReference<List<Wishlist>>() {});
    }

    public static WishlistDetail getWishlistDetail() throws JsonProcessingException {
        return getObject(load("/wishlist_detail.json"), new TypeReference<WishlistDetail>() {});
    }
}
