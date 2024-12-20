CREATE TABLE orders
(
    ord_id           BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    ord_created_at   TIMESTAMP(6)   NOT NULL,
    ord_status       VARCHAR(255)   NOT NULL
        CONSTRAINT orders_status_check
            CHECK ((ord_status)::text = ANY
                   ((ARRAY ['EXCHANGE_REQUESTED':: character varying,'EM_PROCESSAMENTO':: character varying,
                       'PAGAMENTO_REALIZADO':: character varying, 'PAGAMENTO_REJEITADO':: character varying,
                       'EXCHANGE_COMPLETED':: character varying,'EM_TRANSPORTE':: character varying, 'ENTREGUE':: character varying])::text[])
                ),
    ord_total_amount NUMERIC(38, 2) NOT NULL,
    ord_updated_at   TIMESTAMP(6),
    user_id          BIGINT         NOT NULL
        CONSTRAINT orders_user_fk
            REFERENCES users
);
