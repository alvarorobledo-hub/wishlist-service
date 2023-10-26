package com.everis.wishlist.repository.impl;

import com.everis.wishlist.entity.Wishlist;
import com.everis.wishlist.repository.UserWishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;

import java.util.*;

import static com.everis.wishlist.constants.SQLFileConstants.FILE_FIND_USER_LIST;
import static com.everis.wishlist.utils.FileHelper.load;

@Repository
@RequiredArgsConstructor
public class UserWishlistRepositoryImpl implements UserWishlistRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public Wishlist findUserWishlist(final UUID userId, final UUID wishlistId) {
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("wishlist_id", wishlistId);

        return jdbcTemplate.queryForObject(load(FILE_FIND_USER_LIST), params, wishListRowMapper());
    }

    private RowMapper<Wishlist> wishListRowMapper() {
        return (rs, rowNum) -> {
            Wishlist wishlist = Wishlist.builder()
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
