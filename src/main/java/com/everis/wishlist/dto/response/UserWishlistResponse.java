package com.everis.wishlist.dto.response;

import com.everis.wishlist.entity.Wishlist;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserWishlistResponse {
    Wishlist wishlist;
}
