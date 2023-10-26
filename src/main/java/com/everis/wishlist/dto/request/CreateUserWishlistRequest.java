package com.everis.wishlist.dto.request;

import lombok.Value;

import java.util.List;

@Value
public class CreateUserWishlistRequest {
    String name;
    List<Long> productIds;
}
