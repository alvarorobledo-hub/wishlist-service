package com.everis.wishlist.entity;

import lombok.Builder;
import lombok.Data;
import lombok.extern.jackson.Jacksonized;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@Jacksonized
public class Wishlist {
    UUID id;
    String name;
    List<Long> productIds;
}
