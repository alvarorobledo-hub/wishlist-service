package com.everis.wishlist.mock;

import com.everis.wishlist.dto.request.CreateUserWishlistRequest;
import com.everis.wishlist.dto.request.CreateWishlistProductRequest;
import com.everis.wishlist.entity.Product;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.entity.WishlistDetail;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;

import java.util.List;

import static com.everis.wishlist.utils.FileHelper.load;
import static com.everis.wishlist.utils.ObjectMapperHelper.getObject;

public class WishlistServiceMock {

    public static String getStringProduct(final Integer id) {
        return load("/products/product" + id + ".json");
    }

    public static Product getProduct(final Integer id) throws JsonProcessingException {
        return getObject(load("/products/product" + id + ".json"), new TypeReference<Product>() {});
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

    public static CreateUserWishlistRequest getCreateUserWishlistRequest() throws JsonProcessingException {
        return getObject(load("/requests/create_user_wishlist.json"), new TypeReference<CreateUserWishlistRequest>() {});
    }

    public static CreateWishlistProductRequest getCreateWishlistProductRequest() throws JsonProcessingException {
        return getObject(load("/requests/create_wishlist_product.json"), new TypeReference<CreateWishlistProductRequest>() {});
    }
}
