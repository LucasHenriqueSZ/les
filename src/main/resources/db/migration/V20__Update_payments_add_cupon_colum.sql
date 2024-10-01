ALTER TABLE payments
    ADD COLUMN cupon_id BIGINT NULL;

ALTER TABLE payments
    ADD CONSTRAINT payments_cupon_fk
        FOREIGN KEY (cupon_id) REFERENCES cupons;