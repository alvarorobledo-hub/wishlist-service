package com.everis.wishlist.entity;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class Product {
    Long id;
    String name;
    String size;
    String price;
    String color;
}
