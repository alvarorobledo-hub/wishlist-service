package com.everis.wishlist.controller;

import com.everis.wishlist.dto.response.UserWishlistDetailResponse;
import com.everis.wishlist.dto.response.UserWishlistsResponse;
import com.everis.wishlist.service.UserWishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users/{userId}/wishlists")
@RequiredArgsConstructor
public class UserWishlistController {

    private final UserWishlistService wishlistService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUserWishlist(@PathVariable UUID userId, @ResponseBody CreateUserWislistRequest body) {
        return new ResponseEntity<>(wishlistService.createUserWishlist(userId, body));
    }

    @GetMapping(value = "/{wishlistId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserWishlistDetailResponse> getUserWishlist(@PathVariable UUID userId, @PathVariable UUID wishlistId) {
        return new ResponseEntity<>(wishlistService.findUserWishlist(userId, wishlistId), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserWishlistsResponse> getUserWishlists(@PathVariable UUID userId) {
        return new ResponseEntity<>(wishlistService.findUserWishlists(userId), HttpStatus.OK);
    }
}
