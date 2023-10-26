package com.everis.wishlist.controller;

import com.everis.wishlist.dto.response.UserWishlistDetailResponse;
import com.everis.wishlist.entity.WishlistDetail;
import com.everis.wishlist.service.UserWishlistService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.everis.wishlist.constants.WishlistServiceConstants.USER_ID;
import static com.everis.wishlist.constants.WishlistServiceConstants.WISHLIST_ID;
import static com.everis.wishlist.mock.WishlistServiceMock.getWishlistDetail;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class UserWishlistControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserWishlistService userWishlistService;

    @Test
    void should_endpoint_get_user_wishlist_return_wishlist() throws Exception {
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

}
