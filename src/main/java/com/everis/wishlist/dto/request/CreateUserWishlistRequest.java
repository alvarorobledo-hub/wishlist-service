package com.everis.wishlist.dto.request;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class CreateUserWishlistRequest {
    String name;
    List<Long> productIds;
}
