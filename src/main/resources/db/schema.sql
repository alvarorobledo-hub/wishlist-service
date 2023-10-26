CREATE TABLE IF NOT EXISTS WISHLIST (
    id UUID PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE IF NOT EXISTS USER_WISHLIST (
    user_id UUID,
    wishlist_id UUID,
    FOREIGN KEY (wishlist_id) REFERENCES WISHLIST(id) ON DELETE CASCADE,
    PRIMARY KEY (user_id, wishlist_id)
);

CREATE TABLE IF NOT EXISTS WISHLIST_PRODUCT (
    wishlist_id UUID,
    product_id BIGINT,
    FOREIGN KEY (wishlist_id) REFERENCES WISHLIST(id) ON DELETE CASCADE,
    PRIMARY KEY (wishlist_id, product_id)
);

INSERT INTO WISHLIST VALUES ('21b37e55-8d5f-4c0c-a90f-e3e46aa17404', 'test-1');
INSERT INTO WISHLIST VALUES ('772fe05b-54f9-4925-bbb1-a9d4d3d0d8a4', 'test-2');
INSERT INTO WISHLIST VALUES ('14735ac4-96b1-4ea5-b321-e6287774c91e', 'test-3');

INSERT INTO WISHLIST_PRODUCT VALUES ('21b37e55-8d5f-4c0c-a90f-e3e46aa17404', 5);
INSERT INTO WISHLIST_PRODUCT VALUES ('21b37e55-8d5f-4c0c-a90f-e3e46aa17404', 7);
INSERT INTO WISHLIST_PRODUCT VALUES ('21b37e55-8d5f-4c0c-a90f-e3e46aa17404', 2);
INSERT INTO WISHLIST_PRODUCT VALUES ('772fe05b-54f9-4925-bbb1-a9d4d3d0d8a4', 4);
INSERT INTO WISHLIST_PRODUCT VALUES ('772fe05b-54f9-4925-bbb1-a9d4d3d0d8a4', 1);
INSERT INTO WISHLIST_PRODUCT VALUES ('14735ac4-96b1-4ea5-b321-e6287774c91e', 10);
INSERT INTO WISHLIST_PRODUCT VALUES ('14735ac4-96b1-4ea5-b321-e6287774c91e', 12);
INSERT INTO WISHLIST_PRODUCT VALUES ('14735ac4-96b1-4ea5-b321-e6287774c91e', 8);
INSERT INTO WISHLIST_PRODUCT VALUES ('14735ac4-96b1-4ea5-b321-e6287774c91e', 2);

INSERT INTO USER_WISHLIST VALUES ('cb548e14-91da-44ca-9361-bde602a3b6ab', '21b37e55-8d5f-4c0c-a90f-e3e46aa17404');
INSERT INTO USER_WISHLIST VALUES ('cb548e14-91da-44ca-9361-bde602a3b6ab', '772fe05b-54f9-4925-bbb1-a9d4d3d0d8a4');
INSERT INTO USER_WISHLIST VALUES ('cb548e14-91da-44ca-9361-bde602a3b6ab', '14735ac4-96b1-4ea5-b321-e6287774c91e');
