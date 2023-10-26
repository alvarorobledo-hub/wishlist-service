package com.everis.wishlist.dto.request;

import lombok.Value;

import java.util.List;

@Value
public class CreateListRequest {
    String name;
    List<Long> productIds;
}
