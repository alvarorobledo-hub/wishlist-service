package com.everis.wishlist.repository.impl;

import com.everis.wishlist.entity.Wishlist;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.everis.wishlist.constants.WishlistServiceConstants.USER_ID;
import static com.everis.wishlist.constants.WishlistServiceConstants.WISHLIST_ID;
import static com.everis.wishlist.mock.WishlistServiceMock.getWishlist;
import static com.everis.wishlist.mock.WishlistServiceMock.getWishlists;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserWishlistRepositoryImplTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserWishlistRepositoryImpl userWishlistRepository;

    @Test
    void should_return_a_user_wishlist() throws JsonProcessingException {
        final Wishlist wishlist = getWishlist();
        final Map<String, Object> params = getUserWishlistParams();

        // GIVEN
        doReturn(wishlist).when(jdbcTemplate).queryForObject(any(String.class), eq(params), ArgumentMatchers.<RowMapper<Wishlist>>any());

        // WHEN
        final Wishlist response = userWishlistRepository.findUserWishlist(USER_ID, WISHLIST_ID);

        // THEN
        assertAll("Response should be:",
                () -> assertEquals(wishlist, response));
        verify(jdbcTemplate).queryForObject(anyString(), eq(params), ArgumentMatchers.<RowMapper<Wishlist>>any());
    }

    @Test
    void should_return_user_wishlists() throws JsonProcessingException {
        final List<Wishlist> wishlists = getWishlists();
        final List<Map<String, Object>> wishlistsMap = getWishlistsMap(wishlists);
        final Map<String, Object> params = getUserParams();

        // GIVEN
        doReturn(wishlistsMap).when(jdbcTemplate).queryForList(any(String.class), eq(params));

        // WHEN
        final List<Wishlist> response = userWishlistRepository.findUserWishlists(USER_ID);

        // THEN
        assertAll("Response should be:",
                () -> assertEquals(wishlists, response));
        verify(jdbcTemplate).queryForList(anyString(), eq(params));
    }

    private Map<String, Object> getUserWishlistParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", USER_ID);
        params.put("wishlist_id", WISHLIST_ID);

        return params;
    }

    private Map<String, Object> getUserParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", USER_ID);

        return params;
    }

    private List<Map<String, Object>> getWishlistsMap(final List<Wishlist> wishlists) {
        final List<Map<String, Object>> wishlistsMap = new ArrayList<>();

        for(Wishlist wishlist : wishlists) {
            Map<String, Object> map = new HashMap<>();
            map.put("ID", wishlist.getId());
            map.put("NAME", wishlist.getName());

            wishlistsMap.add(map);
        }

        return wishlistsMap;
    }

}
