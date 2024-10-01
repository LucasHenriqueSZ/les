create table users_roles
(
    user_id bigint not null
        constraint fk_users_roles_user_id references users,
    role_id bigint not null
        constraint fk_users_roles_role_id references roles
);

