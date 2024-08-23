package com.les.vest_fut.model.users;

import com.les.vest_fut.Enums.Gender;
import com.les.vest_fut.annotations.ValidPassword;
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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "{NotBlank.client.name}")
    @Size(max = 100, message = "{Size.client.name}")
    @Column(name = "user_name", nullable = false)
    private String name;

    @NotBlank(message = "{NotBlank.client.email}")
    @Email(message = "{Email.client.email}")
    @Column(name = "user_email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "{NotBlank.client.cpf}")
    @CPF(message = "{Pattern.client.cpf}")
    @Column(name = "user_cpf", nullable = false, unique = true)
    private String cpf;

    @NotNull(message = "{NotNull.client.gender}")
    @Enumerated(EnumType.STRING)
    @Column(name = "user_gender", nullable = false)
    private Gender gender;

    @NotBlank(message = "{NotBlank.client.phone}")
    @Size(max = 15, message = "{Size.client.phone}")
    @Column(name = "user_phone", nullable = false)
    private String phone;

    @Valid
    @Size(min = 1, message = "{Size.client.addresses}")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private List<Address> addresses = new ArrayList<>();

    @Valid
    @Size(min = 1, message = "{Size.client.cards}")
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

    @NotBlank(message = "{NotBlank.client.password}")
    @ValidPassword
    @Column(name = "user_password", nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "users_roles",
            joinColumns = @JoinColumn(name = "user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
    private List<Role> roles = new ArrayList<>();

    @AssertTrue(message = "{client.password.mismatch}")
    public boolean isPasswordsMatching() {
        return this.password != null && this.password.equals(this.confirmPassword);
    }
}
