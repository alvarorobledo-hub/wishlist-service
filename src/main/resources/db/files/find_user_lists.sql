SELECT W.NAME, WP.PRODUCT_ID
FROM WISHLIST_PRODUCT WP
INNER JOIN WISHLIST W ON W.ID = UW.WISHLIST_ID
INNER JOIN USER_WISHLIST UW ON UW.WISHLIST_ID = WP.WISHLIST_ID
WHERE UW.USER_ID = :user_id
