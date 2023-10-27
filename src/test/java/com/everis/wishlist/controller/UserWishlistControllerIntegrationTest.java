package com.everis.wishlist.controller;

import com.everis.wishlist.dto.request.CreateUserWishlistRequest;
import com.everis.wishlist.dto.request.CreateWishlistProductRequest;
import com.everis.wishlist.dto.response.UserWishlistDetailResponse;
import com.everis.wishlist.dto.response.UserWishlistsResponse;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.entity.WishlistDetail;
import com.everis.wishlist.service.UserWishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.everis.wishlist.constants.WishlistServiceConstants.USER_ID;
import static com.everis.wishlist.constants.WishlistServiceConstants.WISHLIST_ID;
import static com.everis.wishlist.mock.WishlistServiceMock.*;
import static com.everis.wishlist.utils.ObjectMapperHelper.getString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserWishlistControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserWishlistService userWishlistService;

    @Test
    void should_endpoint_create_user_wishlist() throws Exception {
        final CreateUserWishlistRequest body = getCreateUserWishlistRequest();

        // GIVEN
        doNothing().when(userWishlistService).createUserWishlist(USER_ID, WISHLIST_ID, body);

        // WHEN, THEN
        mockMvc.perform(post("/api/v1/users/{userId}/wishlists/{wishlistId}", USER_ID, WISHLIST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getString(body)))
                .andExpect(status().isCreated());
    }

    @Test
    void should_endpoint_create_wishlist_product() throws Exception {
        final CreateWishlistProductRequest body = getCreateWishlistProductRequest();

        // GIVEN
        doNothing().when(userWishlistService).createUserWishlistProduct(USER_ID, WISHLIST_ID, body);

        // WHEN, THEN
        mockMvc.perform(post("/api/v1/users/{userId}/wishlists/{wishlistId}/products", USER_ID, WISHLIST_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getString(body)))
                .andExpect(status().isCreated());
    }

    @Test
    void should_endpoint_get_user_wishlist_return_a_user_wishlist() throws Exception {
        final WishlistDetail wishlistDetail = getWishlistDetail();
        final UserWishlistDetailResponse response = UserWishlistDetailResponse.builder()
                .wishlistDetail(wishlistDetail)
                .build();

        // GIVEN
        given(userWishlistService.findUserWishlist(USER_ID, WISHLIST_ID)).willReturn(response);

        // WHEN, THEN
        mockMvc.perform(get("/api/v1/users/{userId}/wishlists/{wishlistId}", USER_ID, WISHLIST_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void should_endpoint_get_user_wishlists_return_user_wishlists() throws Exception {
        final List<Wishlist> wishlists = getWishlists();
        final UserWishlistsResponse response = UserWishlistsResponse.builder()
                .wishlists(wishlists)
                .build();

        // GIVEN
        given(userWishlistService.findUserWishlists(USER_ID)).willReturn(response);

        // WHEN, THEN
        mockMvc.perform(get("/api/v1/users/{userId}/wishlists", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

}
