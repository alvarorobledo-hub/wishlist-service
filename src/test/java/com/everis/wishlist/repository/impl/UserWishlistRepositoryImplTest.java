package com.everis.wishlist.repository.impl;

import com.everis.wishlist.dto.request.CreateUserWishlistRequest;
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

import java.util.*;

import static com.everis.wishlist.constants.WishlistServiceConstants.*;
import static com.everis.wishlist.mock.WishlistServiceMock.*;
import static com.everis.wishlist.utils.FileHelper.load;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserWishlistRepositoryImplTest {

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @InjectMocks
    private UserWishlistRepositoryImpl userWishlistRepository;

    @Test
    void should_create_user_wishlist() throws JsonProcessingException {

        final CreateUserWishlistRequest body = getCreateUserWishlistRequest();
        final Map<String, Object> wishlistParams = getWishlistNameParams();
        final Map<String, Object> userWishlistParams = getUserWishlistParams();

        // GIVEN
        doReturn(1).when(jdbcTemplate).update(load(FILE_CREATE_WISHLIST), wishlistParams);
        doReturn(1).when(jdbcTemplate).update(load(FILE_CREATE_USER_WISHLIST), userWishlistParams);

        // WHEN
        userWishlistRepository.createWishlist(USER_ID, WISHLIST_ID, body.getName());

        // THEN
        verify(jdbcTemplate).update(load(FILE_CREATE_WISHLIST), wishlistParams);
        verify(jdbcTemplate).update(load(FILE_CREATE_USER_WISHLIST), userWishlistParams);
    }

    @Test
    void should_create_user_wishlist_product() {

        final Map<String, Object> params = getWishlistProductParams();

        // GIVEN
        doReturn(1).when(jdbcTemplate).update(load(FILE_CREATE_WISHLIST_PRODUCT), params);

        // WHEN
        userWishlistRepository.createWishlistProduct(WISHLIST_ID, 1L);

        // THEN
        verify(jdbcTemplate).update(load(FILE_CREATE_WISHLIST_PRODUCT), params);
    }

    @Test
    void should_delete_user_wishlist() {

        final Map<String, Object> wishlistParams = getWishlistParams();

        // GIVEN
        doReturn(1).when(jdbcTemplate).update(load(FILE_DELETE_USER_WISHLIST), wishlistParams);

        // WHEN
        userWishlistRepository.deleteUserWishlist(WISHLIST_ID);

        // THEN
        verify(jdbcTemplate).update(load(FILE_DELETE_USER_WISHLIST), wishlistParams);
    }

    @Test
    void should_return_a_user_wishlist() throws JsonProcessingException {
        final Wishlist wishlist = getWishlist();
        final Map<String, Object> params = getUserWishlistParams();

        // GIVEN
        doReturn(wishlist).when(jdbcTemplate).queryForObject(eq(load(FILE_FIND_USER_WISHLIST)), eq(params), ArgumentMatchers.<RowMapper<Wishlist>>any());

        // WHEN
        final Wishlist response = userWishlistRepository.findUserWishlist(USER_ID, WISHLIST_ID);

        // THEN
        assertAll("Response should be:",
                () -> assertEquals(wishlist, response));
        verify(jdbcTemplate).queryForObject(eq(load(FILE_FIND_USER_WISHLIST)), eq(params), ArgumentMatchers.<RowMapper<Wishlist>>any());
    }

    @Test
    void should_return_user_wishlists() throws JsonProcessingException {
        final List<Wishlist> wishlists = getWishlists();
        final List<Map<String, Object>> wishlistsMap = getWishlistsMap(wishlists);
        final Map<String, Object> params = getUserParams();

        // GIVEN
        doReturn(wishlistsMap).when(jdbcTemplate).queryForList(load(FILE_FIND_USER_WISHLISTS), params);

        // WHEN
        final List<Wishlist> response = userWishlistRepository.findUserWishlists(USER_ID);

        // THEN
        assertAll("Response should be:",
                () -> assertEquals(wishlists, response));
        verify(jdbcTemplate).queryForList(load(FILE_FIND_USER_WISHLISTS), params);
    }

    private Map<String, Object> getWishlistParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", WISHLIST_ID);

        return params;
    }

    private Map<String, Object> getWishlistNameParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("id", WISHLIST_ID);
        params.put("name", WISHLIST_NAME);

        return params;
    }

    private Map<String, Object> getUserWishlistParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", USER_ID);
        params.put("wishlist_id", WISHLIST_ID);

        return params;
    }

    private Map<String, Object> getWishlistProductParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("wishlist_id", WISHLIST_ID);
        params.put("product_id", 1L);

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
