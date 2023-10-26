package com.everis.wishlist.entity;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;

@Data
@Builder
@Jacksonized
public class Wishlist {
    String name;
    List<Long> productIds;
}
