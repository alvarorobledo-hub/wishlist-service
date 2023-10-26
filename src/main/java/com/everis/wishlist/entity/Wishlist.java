package com.everis.wishlist.entity;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Value
@Builder
@Jacksonized
public class Wishlist {
    String name;
    List<Product> products;
}
