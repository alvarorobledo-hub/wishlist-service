package com.everis.wishlist.mapper;

import com.everis.wishlist.client.ProductApiClient;
import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.entity.WishlistDetail;
import com.everis.wishlist.repository.UserWishlistRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import feign.FeignException;
import feign.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static com.everis.wishlist.constants.WishlistServiceConstants.PRODUCT_ID;
import static com.everis.wishlist.constants.WishlistServiceConstants.WISHLIST_ID;
import static com.everis.wishlist.mock.WishlistServiceMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WishlistMapperTest {

    @Mock
    private UserWishlistRepository userWishlistRepository;
    @Mock
    private ProductApiClient productApiClient;

    @InjectMocks
    private WishlistMapper wishlistMapper;

    @Test
    void should_map_a_wishlist_to_wishlist_detail() throws JsonProcessingException {

        final Wishlist wishlist = getWishlist();
        final WishlistDetail wishlistDetail = getWishlistDetail();

        // GIVEN
        doReturn(getStringProduct(4)).when(productApiClient).getProduct(4L);
        doReturn(getStringProduct(5)).when(productApiClient).getProduct(5L);
        doReturn(getStringProduct(6)).when(productApiClient).getProduct(6L);

        // WHEN
        final WishlistDetail response = wishlistMapper.from(wishlist);

        // THEN
        assertAll("Mapper should be:",
                () -> assertEquals(wishlistDetail, response));
        verify(userWishlistRepository, never()).deleteUserWishlistProduct(WISHLIST_ID, PRODUCT_ID);
    }

    @Test
    void should_delete_a_product_and_return_a_wishlist_without_it() throws JsonProcessingException {
        final Wishlist wishlist = getWishlist();
        final WishlistDetail wishlistDetail = getWishlistDetail();
        FeignException.NotFound notFoundException = Mockito.mock(FeignException.NotFound.class);

        // GIVEN
        wishlist.getProductIds().add(22L);
        doReturn(getStringProduct(4)).when(productApiClient).getProduct(4L);
        doReturn(getStringProduct(5)).when(productApiClient).getProduct(5L);
        doReturn(getStringProduct(6)).when(productApiClient).getProduct(6L);
        doThrow(notFoundException).when(productApiClient).getProduct(22L);

        // WHEN
        final WishlistDetail response = wishlistMapper.from(wishlist);

        // THEN
        assertAll("Mapper should be:",
                () -> assertEquals(wishlistDetail, response));
        verify(userWishlistRepository).deleteUserWishlistProduct(WISHLIST_ID, 22L);
    }

}
