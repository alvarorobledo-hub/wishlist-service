package com.everis.wishlist.entity;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class WishlistDetail {
    String name;
    List<Product> productIds;
}
