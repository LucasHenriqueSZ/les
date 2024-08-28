package com.les.vest_fut.model.users;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "adr_id")
    private Long id;

    @NotBlank(message = "{NotBlank.address.zipCode}")
    @Size(max = 10, message = "{Size.address.zipCode}")
    @Column(name = "adr_zip_code", nullable = false)
    private String zipCode;

    @NotBlank(message = "{NotBlank.address.street}")
    @Size(max = 100, message = "{Size.address.street}")
    @Column(name = "adr_street", nullable = false)
    private String street;

    @NotBlank(message = "{NotBlank.address.number}")
    @Size(max = 10, message = "{Size.address.number}")
    @Column(name = "adr_number", nullable = false)
    private String number;

    @Size(max = 50, message = "{Size.address.complement}")
    @Column(name = "adr_complement")
    private String complement;

    @NotBlank(message = "{NotBlank.address.neighborhood}")
    @Size(max = 50, message = "{Size.address.neighborhood}")
    @Column(name = "adr_neighborhood", nullable = false)
    private String neighborhood;

    @NotBlank(message = "{NotBlank.address.city}")
    @Size(max = 50, message = "{Size.address.city}")
    @Column(name = "adr_city", nullable = false)
    private String city;

    @NotBlank(message = "{NotBlank.address.state}")
    @Size(max = 2, message = "{Size.address.state}")
    @Column(name = "adr_state", nullable = false)
    private String state;

    public Address() {
    }

    public Address(String zipCode, String street, String number, String complement, String neighborhood, String city, String state) {
        setZipCode(zipCode);
        setStreet(street);
        setNumber(number);
        setComplement(complement);
        setNeighborhood(neighborhood);
        setCity(city);
        setState(state);
    }

}
