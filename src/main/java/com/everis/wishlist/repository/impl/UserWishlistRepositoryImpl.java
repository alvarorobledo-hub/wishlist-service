package com.everis.wishlist.repository.impl;

import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.repository.UserWishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.everis.wishlist.constants.SQLFileConstants.*;
import static com.everis.wishlist.utils.FileHelper.load;

@Repository
@RequiredArgsConstructor
public class UserWishlistRepositoryImpl implements UserWishlistRepository {

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PRODUCT_ID = "product_id";
    private static final String WISHLIST_ID = "wishlist_id";
    private static final String USER_ID = "user_id";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void createWishlist(final UUID userId, final UUID wishlistId, final String name) {

        insertWishlist(wishlistId, name);
        insertUserWishlist(userId, wishlistId);

    }

    @Override
    public void createWishlistProduct(final UUID wishlistId, final Long productId) {
        Map<String, Object> params = new HashMap<>();
        params.put(WISHLIST_ID, wishlistId);
        params.put(PRODUCT_ID, productId);

        jdbcTemplate.update(load(FILE_CREATE_WISHLIST_PRODUCT), params);
    }

    @Override
    public void deleteUserWishlist(final UUID wishlistId) {
        Map<String, Object> params = new HashMap<>();
        params.put(ID, wishlistId);

        jdbcTemplate.update(load(FILE_DELETE_USER_WISHLIST), params);
    }

    @Override
    public void deleteUserWishlistProduct(final UUID wishlistId, final Long productId) {
        Map<String, Object> params = new HashMap<>();
        params.put(WISHLIST_ID, wishlistId);
        params.put(PRODUCT_ID, productId);

        jdbcTemplate.update(load(FILE_DELETE_USER_WISHLIST_PRODUCT), params);
    }

    @Override
    public Wishlist findUserWishlist(final UUID userId, final UUID wishlistId) {
        Map<String, Object> params = new HashMap<>();
        params.put(USER_ID, userId);
        params.put(WISHLIST_ID, wishlistId);

        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(load(FILE_FIND_USER_WISHLIST), params);

        Wishlist wishlist = Wishlist.builder()
                .productIds(new ArrayList<>())
                .build();
        for(Map<String, Object> row : rows) {
            if (wishlist.getId() == null) {
                wishlist.setId((UUID) row.get("ID"));
                wishlist.setName((String) row.get("NAME"));
            }
            wishlist.getProductIds().add((Long) row.get("PRODUCT_ID"));
        }

        return wishlist;
    }

    @Override
    public List<Wishlist> findUserWishlists(final UUID userId) {
        Map<String, Object> params = new HashMap<>();
        params.put(USER_ID, userId);

        final List<Map<String, Object>> rows = jdbcTemplate.queryForList(load(FILE_FIND_USER_WISHLISTS), params);
        return rows.stream()
                .map(row -> Wishlist.builder()
                        .id((UUID) row.get("ID"))
                        .name((String) row.get("NAME"))
                        .build())
                .collect(Collectors.toList());
    }

    private void insertWishlist(final UUID wishlistId, final String name) {
        Map<String, Object> params = new HashMap<>();
        params.put(ID, wishlistId);
        params.put(NAME, name);

        jdbcTemplate.update(load(FILE_CREATE_WISHLIST), params);
    }

    private void insertUserWishlist(final UUID userId, final UUID wishlistId) {
        Map<String, Object> params = new HashMap<>();
        params.put(USER_ID, userId);
        params.put(WISHLIST_ID, wishlistId);

        jdbcTemplate.update(load(FILE_CREATE_USER_WISHLIST), params);
    }
}
