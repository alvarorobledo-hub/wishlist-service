package com.everis.wishlist.validator;

import com.everis.wishlist.dto.request.CreateUserWishlistRequest;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.exceptions.MaxWishlistsPerUserException;
import com.everis.wishlist.exceptions.http.BadRequestException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.UUID;

@Component
public class UserWishlistValidator {

    private static final Integer MAX_WISHLISTS_PER_USER = 5;

    public void validate(final UUID userId, final CreateUserWishlistRequest body, final List<Wishlist> wishlists) {

        if (ObjectUtils.isEmpty(body.getName())) {
            throw new BadRequestException("Wishlist name must not be void");
        }

        if (body.getProductIds().size() == 0) {
            throw new BadRequestException("ProductIds must have minimum one product");
        }

        if (wishlists.size() >= MAX_WISHLISTS_PER_USER) {
            throw new MaxWishlistsPerUserException("User %s actually have %s wishlists. Cannot create more", userId, MAX_WISHLISTS_PER_USER);
        }
    }
}
