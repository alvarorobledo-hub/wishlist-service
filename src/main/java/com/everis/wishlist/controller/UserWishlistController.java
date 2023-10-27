package com.everis.wishlist.controller;

import com.everis.wishlist.dto.request.CreateUserWishlistRequest;
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

    private final UserWishlistService userWishlistService;

    @PostMapping(value = "/{wishlistId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUserWishlist(@PathVariable UUID userId, @PathVariable UUID wishlistId, @RequestBody CreateUserWishlistRequest body) {
        userWishlistService.createUserWishlist(userId, wishlistId, body);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping(value = "/{wishlistId}/products/{productId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUserWishlistProduct(@PathVariable UUID userId, @PathVariable UUID wishlistId, @PathVariable Long productId) {
        userWishlistService.createUserWishlistProduct(userId, wishlistId, productId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/{wishlistId}")
    public ResponseEntity<Void> deleteUserWishlist(@PathVariable UUID userId, @PathVariable UUID wishlistId) {
        userWishlistService.deleteUserWishlist(userId, wishlistId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{wishlistId}/products/{productId}")
    public ResponseEntity<Void> deleteUserWishlistProduct(@PathVariable UUID userId, @PathVariable UUID wishlistId, @PathVariable Long productId) {
        userWishlistService.deleteUserWishlistProduct(userId, wishlistId, productId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/{wishlistId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserWishlistDetailResponse> getUserWishlist(@PathVariable UUID userId, @PathVariable UUID wishlistId) {
        return new ResponseEntity<>(userWishlistService.findUserWishlist(userId, wishlistId), HttpStatus.OK);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserWishlistsResponse> getUserWishlists(@PathVariable UUID userId) {
        return new ResponseEntity<>(userWishlistService.findUserWishlists(userId), HttpStatus.OK);
    }
}
