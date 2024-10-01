INSERT INTO users (user_active, user_cpf, user_created_at, user_email, user_gender, user_name, user_password,
                          user_phone, user_updated_at)
VALUES (true, '123.456.789-00', NOW(), 'user@example.com', 'MALE', 'Common User',
    --  User123@
        '$2a$10$Jru0GTF6XKz/9sL8TE4xKucuw5YKhhUEXPuPoQ6Z4Ua.LX.2vvs5C',
        '(11) 9876-5432', NOW());

INSERT INTO addresses (adr_city, adr_complement, adr_neighborhood, adr_number, adr_state, adr_street,
                              adr_zip_code, user_id)
VALUES ('Common City', 'House 42', 'Suburb', '200', 'RJ', 'Common Street', '12345-678', 2);

INSERT INTO cards (crd_number, crd_cvv, crd_expiry_date, crd_flag, crd_holder_name, user_id)
VALUES ('5555666677778888', '321', '11/26', 'MASTERCARD', 'Common User', 2);

INSERT INTO users_roles (user_id, role_id)
VALUES (2, 2);