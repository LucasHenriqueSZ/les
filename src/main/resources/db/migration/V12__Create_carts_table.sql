CREATE TABLE carts
(
    crt_id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    crt_created_at TIMESTAMP(6) NOT NULL,
    crt_updated_at TIMESTAMP(6),
    user_id        BIGINT       NOT NULL
        CONSTRAINT carts_user_fk
            REFERENCES users
);