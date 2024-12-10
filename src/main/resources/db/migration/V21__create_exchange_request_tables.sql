CREATE TABLE exchange_requests
(
    exr_id         BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    exr_order_id   BIGINT       NOT NULL,
    exr_user_id    BIGINT       NOT NULL,
    exr_status     VARCHAR(255) NOT NULL,
    exr_reason     VARCHAR(255) NOT NULL,
    exr_created_at TIMESTAMP    NOT NULL,
    exr_updated_at TIMESTAMP,
    exr_completed_at TIMESTAMP,
    exr_observations TEXT,

    CONSTRAINT fk_exchange_requests_order FOREIGN KEY (exr_order_id) REFERENCES orders (ord_id),
    CONSTRAINT fk_exchange_requests_user FOREIGN KEY (exr_user_id) REFERENCES users (id)
);

CREATE TABLE exchange_request_items
(
    eri_id                  BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    eri_exchange_request_id BIGINT NOT NULL,
    eri_ori_id              BIGINT NOT NULL,
    eri_quantity            INT    NOT NULL,

    CONSTRAINT fk_exchange_request_items_exchange_request FOREIGN KEY (eri_exchange_request_id) REFERENCES exchange_requests (exr_id),
    CONSTRAINT fk_exchange_request_items_order_item FOREIGN KEY (eri_ori_id) REFERENCES order_items (ori_id)
);