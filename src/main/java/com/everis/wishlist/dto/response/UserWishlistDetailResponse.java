package com.everis.wishlist.dto.response;

import com.everis.wishlist.entity.WishlistDetail;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserWishlistDetailResponse {
    WishlistDetail wishlistDetail;
}
