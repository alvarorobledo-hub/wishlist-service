package com.everis.wishlist.repository.impl;

import com.everis.wishlist.dto.request.CreateUserWishlistRequest;
import com.everis.wishlist.entity.Wishlist;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.everis.wishlist.constants.WishlistServiceConstants.*;
import static com.everis.wishlist.mock.WishlistServiceMock.*;
import static com.everis.wishlist.utils.FileHelper.load;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

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
        userWishlistRepository.createWishlistProduct(WISHLIST_ID, PRODUCT_ID);

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
    void should_delete_user_wishlist_product() {

        final Map<String, Object> wishlistParams = getWishlistProductParams();

        // GIVEN
        doReturn(1).when(jdbcTemplate).update(load(FILE_DELETE_USER_WISHLIST_PRODUCT), wishlistParams);

        // WHEN
        userWishlistRepository.deleteUserWishlistProduct(WISHLIST_ID, PRODUCT_ID);

        // THEN
        verify(jdbcTemplate).update(load(FILE_DELETE_USER_WISHLIST_PRODUCT), wishlistParams);
    }

    @Test
    void should_return_a_user_wishlist() throws JsonProcessingException {
        final Wishlist wishlist = getWishlist();
        final List<Map<String, Object>> wishlistMap = getWishlistMap(wishlist);
        final Map<String, Object> params = getUserWishlistParams();

        // GIVEN
        doReturn(wishlistMap).when(jdbcTemplate).queryForList(load(FILE_FIND_USER_WISHLIST), params);

        // WHEN
        final Wishlist response = userWishlistRepository.findUserWishlist(USER_ID, WISHLIST_ID);

        // THEN
        assertAll("Response should be:",
                () -> assertEquals(wishlist, response));
        verify(jdbcTemplate).queryForList(load(FILE_FIND_USER_WISHLIST), params);
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
        params.put("product_id", PRODUCT_ID);

        return params;
    }

    private Map<String, Object> getUserParams() {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", USER_ID);

        return params;
    }

    private List<Map<String, Object>> getWishlistsMap(final List<Wishlist> wishlists) {
        return wishlists.stream()
                .map(wishlist -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("ID", wishlist.getId());
                    map.put("NAME", wishlist.getName());
                    return map;
                })
                .collect(Collectors.toList());
    }

    private List<Map<String, Object>> getWishlistMap(final Wishlist wishlist) {
        return wishlist.getProductIds().stream()
                .map(productId -> {
                    Map<String, Object> map = new HashMap<>();
                    map.put("ID", wishlist.getId());
                    map.put("NAME", wishlist.getName());
                    map.put("PRODUCT_ID", productId);
                    return map;
                })
                .collect(Collectors.toList());
    }

}
