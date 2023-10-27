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

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void createWishlist(final UUID userId, final UUID wishlistId, final String name) {

        insertWishlist(wishlistId, name);
        insertUserWishlist(userId, wishlistId);

    }

    @Override
    public void createWishlistProduct(final UUID wishlistId, final Long productId) {
        Map<String, Object> params = new HashMap<>();
        params.put("wishlist_id", wishlistId);
        params.put("product_id", productId);

        jdbcTemplate.update(load(FILE_CREATE_WISHLIST_PRODUCT), params);
    }

    @Override
    public void deleteUserWishlist(final UUID wishlistId) {
        Map<String, Object> params = new HashMap<>();
        params.put("id", wishlistId);

        jdbcTemplate.update(load(FILE_DELETE_USER_WISHLIST), params);
    }

    @Override
    public void deleteUserWishlistProduct(final UUID wishlistId, final Long productId) {
        Map<String, Object> params = new HashMap<>();
        params.put("wishlist_id", wishlistId);
        params.put("product_id", productId);

        jdbcTemplate.update(load(FILE_DELETE_USER_WISHLIST_PRODUCT), params);
    }

    @Override
    public Wishlist findUserWishlist(final UUID userId, final UUID wishlistId) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("wishlist_id", wishlistId);

        return jdbcTemplate.queryForObject(load(FILE_FIND_USER_WISHLIST), params, wishlistRowMapper());
    }

    @Override
    public List<Wishlist> findUserWishlists(final UUID userId) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);

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
        params.put("id", wishlistId);
        params.put("name", name);

        jdbcTemplate.update(load(FILE_CREATE_WISHLIST), params);
    }

    private void insertUserWishlist(final UUID userId, final UUID wishlistId) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("wishlist_id", wishlistId);

        jdbcTemplate.update(load(FILE_CREATE_USER_WISHLIST), params);
    }

    private RowMapper<Wishlist> wishlistRowMapper() {
        return (rs, rowNum) -> {
            Wishlist wishlist = Wishlist.builder()
                    .id(rs.getObject("id", UUID.class))
                    .name(rs.getString("name"))
                    .build();

            List<Long> productIds = new ArrayList<>();
            do {
                final Long productId = rs.getLong("product_id");

                if (!ObjectUtils.isEmpty(productId)) {
                    productIds.add(productId);
                }

            } while (rs.next());

            wishlist.setProductIds(productIds);
            return wishlist;
        };
    }
}
