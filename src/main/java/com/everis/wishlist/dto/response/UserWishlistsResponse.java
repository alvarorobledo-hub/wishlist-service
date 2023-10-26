package com.everis.wishlist.dto.response;

import com.everis.wishlist.entity.Wishlist;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class UserWishlistsResponse {
    List<Wishlist> wishlists;
}
