package com.everis.wishlist.validator;

import com.everis.wishlist.dto.request.CreateUserWishlistRequest;
import com.everis.wishlist.dto.request.CreateWishlistProductRequest;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.entity.WishlistDetail;
import com.everis.wishlist.exceptions.MaxProductsPerWishlistException;
import com.everis.wishlist.exceptions.MaxWishlistsPerUserException;
import com.everis.wishlist.exceptions.http.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserWishlistValidator {

    private static final Integer MAX_WISHLISTS_PER_USER = 5;
    private static final Integer MAX_PRODUCTS_PER_WISHLIST = 25;

    public void validate(final UUID userId, final CreateUserWishlistRequest body, final List<Wishlist> wishlists) {

        if (ObjectUtils.isEmpty(body.getName())) {
            throw new BadRequestException("Wishlist name must not be void");
        }

        if (body.getProductIds().size() == 0) {
            throw new BadRequestException("ProductIds must have minimum one product");
        }

        if (wishlists.size() >= MAX_WISHLISTS_PER_USER) {
            throw new MaxWishlistsPerUserException("User (%s) actually have (%s) wishlists. Cannot create more", userId, MAX_WISHLISTS_PER_USER);
        }
    }

    public void validate(final WishlistDetail wishlist, final CreateWishlistProductRequest body) {

        if (body.getProductId() == null) {
            throw new BadRequestException("ProductId must not be null");
        }

        if (wishlist.getProducts().size() >= MAX_PRODUCTS_PER_WISHLIST) {
            throw new MaxProductsPerWishlistException("Wishlist (%s) actually have (%s) products. Cannot create more", wishlist.getId(), MAX_PRODUCTS_PER_WISHLIST);
        }
    }

    public void validateWishlistOwner(final UUID userId, final UUID wishlistId, final List<Wishlist> wishlists) {

        Optional<Wishlist> optional = wishlists.stream().filter(wishlist -> wishlist.getId().equals(wishlistId)).findAny();

        if (optional.isEmpty()) {
            throw new BadRequestException("User (%s) is not the owner of the wishlist (%s)", userId, wishlistId);
        }
    }
}
