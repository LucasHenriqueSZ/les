create table cards
(
    crd_id          bigint generated by default as identity primary key,
    crd_number      varchar(16)  not null,
    crd_cvv         varchar(4)   not null,
    crd_expiry_date varchar(255) not null,
    crd_flag        varchar(255) not null
        constraint chk_cards_flag check (crd_flag in ('MASTERCARD', 'VISA', 'ELO', 'AMERICAN_EXPRESS', 'HIPERCARD')),
    crd_holder_name varchar(100) not null,
    user_id         bigint
        constraint fk_cards_user_id references users
);