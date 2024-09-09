package com.les.vest_fut.model.users;

import com.les.vest_fut.Enums.Gender;
import com.les.vest_fut.annotations.ValidPassword;
import com.les.vest_fut.utils.groups.OnBasicInfoValidation;
import com.les.vest_fut.utils.groups.OnCreate;
import com.les.vest_fut.utils.groups.OnPasswordValidation;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{NotBlank.client.name}", groups = {OnCreate.class, OnBasicInfoValidation.class})
    @Size(max = 100, min = 3, message = "{Size.client.name}", groups = {OnCreate.class, OnBasicInfoValidation.class})
    @Column(name = "user_name", nullable = false)
    private String name;

    @NotBlank(message = "{NotBlank.client.email}", groups = {OnCreate.class, OnBasicInfoValidation.class})
    @Email(message = "{Email.client.email}", groups = {OnCreate.class, OnBasicInfoValidation.class})
    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "{NotBlank.client.cpf}", groups = {OnCreate.class, OnBasicInfoValidation.class})
    @CPF(message = "{Pattern.client.cpf}", groups = {OnCreate.class, OnBasicInfoValidation.class})
    @Column(name = "user_cpf", nullable = false, unique = true)
    private String cpf;

    @NotNull(message = "{NotNull.client.gender}", groups = {OnCreate.class, OnBasicInfoValidation.class})
    @Enumerated(EnumType.STRING)
    @Column(name = "user_gender", nullable = false)
    private Gender gender;

    @NotBlank(message = "{NotBlank.client.phone}", groups = {OnCreate.class, OnBasicInfoValidation.class})
    @Size(max = 15, message = "{Size.client.phone}", groups = {OnCreate.class, OnBasicInfoValidation.class})
    @Pattern(regexp = "^\\(?(\\d{2})\\)?[- ]?(9?\\d{4})[- ]?(\\d{4})$", message = "{Pattern.client.phone}", groups = {OnCreate.class, OnBasicInfoValidation.class})
    @Column(name = "user_phone", nullable = false)
    private String phone;

    @Valid
    @Size(min = 1, message = "{Size.client.addresses}", groups = {OnCreate.class})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Address> addresses = new ArrayList<>();

    @Valid
    @Size(min = 1, message = "{Size.client.cards}", groups = {OnCreate.class})
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Card> cards = new ArrayList<>();

    @Column(name = "user_active", nullable = false)
    private boolean active = true;

    @CreationTimestamp
    @Column(name = "user_created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "user_updated_at")
    private LocalDateTime updatedAt;

    @NotBlank(message = "{NotBlank.client.password}", groups = {OnCreate.class, OnPasswordValidation.class})
    @ValidPassword(groups = {OnCreate.class, OnPasswordValidation.class})
    @Column(name = "user_password", nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    @AssertTrue(message = "{client.password.mismatch}", groups = {OnCreate.class, OnPasswordValidation.class})
    public boolean isPasswordsMatching() {
        return this.password != null && this.password.equals(this.confirmPassword);
    }

    public boolean hasValidObject() {
        return isNotNullOrEmpty(name) ||
                isNotNullOrEmpty(email) ||
                isNotNullOrEmpty(cpf) ||
                isNotNullOrEmpty(phone) ||
                id != null ||
                (addresses != null && !addresses.isEmpty()) ||
                (cards != null && !cards.isEmpty()) ||
                createdAt != null ||
                updatedAt != null ||
                isNotNullOrEmpty(password) ||
                isNotNullOrEmpty(confirmPassword) ||
                (roles != null && !roles.isEmpty());
    }

    private boolean isNotNullOrEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
}
