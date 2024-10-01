insert into users (user_active, user_cpf, user_created_at, user_email, user_gender, user_name, user_password,
                          user_phone, user_updated_at)
values (true, '455.089.350-88', now(), 'admin@example.com', 'MALE', 'Admin User',
        -- Admin123@
        '$2a$10$sUnTTqMLz97a8KiLtY/AfuZoDIr7sjEpXjMVlON.lsIhRIFmaIr62',
        '(11) 9789-4561', now());

insert into addresses (adr_city, adr_complement, adr_neighborhood, adr_number, adr_state, adr_street,
                              adr_zip_code, user_id)
values ('Admin City', 'Apartment 1', 'Downtown', '100', 'SP', 'Admin Street', '68551-025', 1);

insert into cards (crd_number, crd_cvv, crd_expiry_date, crd_flag, crd_holder_name, user_id)
values ('1111222233334444', '123', '12/28', 'VISA', 'Admin User', 1);

insert into users_roles (user_id, role_id)
values (1, 1),
       (1, 2);
