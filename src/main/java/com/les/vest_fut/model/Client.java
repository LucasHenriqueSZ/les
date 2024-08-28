package com.les.vest_fut.model;

import com.les.vest_fut.Enums.Gender;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Table(name = "clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cli_id")
    private Long id;

    @NotBlank(message = "{NotBlank.client.name}")
    @Size(max = 100, message = "{Size.client.name}")
    @Column(name = "cli_name", nullable = false)
    private String name;

    @NotBlank(message = "{NotBlank.client.cpf}")
    @CPF(message = "{Pattern.client.cpf}")
    @Column(name = "cli_cpf", nullable = false, unique = true)
    private String cpf;

    @NotNull(message = "{NotNull.client.gender}")
    @Enumerated(EnumType.STRING)
    @Column(name = "cli_gender", nullable = false)
    private Gender gender;

    @NotBlank(message = "{NotBlank.client.email}")
    @Email(message = "{Email.client.email}")
    @Column(name = "cli_email", nullable = false, unique = true)
    private String email;

    @NotBlank(message = "{NotBlank.client.phone}")
    @Size(max = 15, message = "{Size.client.phone}")
    @Column(name = "cli_phone", nullable = false)
    private String phone;

    @NotBlank(message = "{NotBlank.client.password}")
    @Size(min = 8, message = "{Size.client.password}")
    @Column(name = "cli_password", nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @CreationTimestamp
    @Column(name = "cli_created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "cli_updated_at")
    private LocalDateTime updatedAt;

    @Valid
    @Size(min = 1, message = "{Size.client.addresses}")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cli_id")
    private List<Address> addresses = new ArrayList<>();

    @Valid
    @Size(min = 1, message = "{Size.client.cards}")
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "cli_id")
    private List<Card> cards = new ArrayList<>();

    @AssertTrue(message = "{client.password.mismatch}")
    public boolean isPasswordsMatching() {
        return this.password != null && this.password.equals(this.confirmPassword);
    }

    public Client(String name, String cpf, Gender gender, String email, String phone, String password, String confirmPassword, List<Address> addresses, List<Card> cards) {
        setName(name);
        setCpf(cpf);
        setGender(gender);
        setEmail(email);
        setPhone(phone);
        setPassword(password);
        setConfirmPassword(confirmPassword);
        setAddresses(addresses);
        setCards(cards);
    }

    public Client() {
    }

}
