package com.everis.wishlist.mapper;

import com.everis.wishlist.client.ProductApiClient;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.entity.WishlistDetail;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.everis.wishlist.mock.WishlistServiceMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class WishlistMapperTest {

    @Mock
    private ProductApiClient productApiClient;

    @InjectMocks
    private WishlistMapper wishlistMapper;

    @Test
    void should_map_a_wishlist_to_wishlist_detail() throws JsonProcessingException {

        final Wishlist wishlist = getWishlist();
        final WishlistDetail wishlistDetail = getWishlistDetail();

        // GIVEN
        doReturn(getProduct(4)).when(productApiClient).getProduct(4L);
        doReturn(getProduct(5)).when(productApiClient).getProduct(5L);
        doReturn(getProduct(6)).when(productApiClient).getProduct(6L);

        // WHEN
        final WishlistDetail response = wishlistMapper.from(wishlist);

        // THEN
        assertAll("Mapper should be:",
                () -> assertEquals(wishlistDetail, response));
    }

}
