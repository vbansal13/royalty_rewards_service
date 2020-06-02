package com.vbansal13.identity_service.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
@Embeddable
@NoArgsConstructor
public class Address {

    @JsonProperty("line1")
    @Column(name = "address_line1")
    @Size(max = 100)
    @NotEmpty(message = "*Please provide unit number and street.")
    private String address1;

    @JsonProperty("line2")
    @Column(name = "address_line2")
    @Size(max = 100)
    private String address2;

    @Column(name = "city")
    @Size(max = 50)
    @NotEmpty(message = "*Please provide your city.")
    private String city;

    @Column(name = "zip_code")
    @Size(max = 20)
    @NotBlank(message = "*Please provide your zipcode.")
    private String zip_code;

    @Column(name = "country")
    @Size(max = 50)
    @NotEmpty(message = "*Please provide your country.")
    private String country;

    @Column(name = "state")
    @Size(max = 50)
    @NotEmpty(message = "*Please provide your state.")
    private String state;

    @Column(name = "phone")
    @Size(min = 10, message = "*Your password must have at least 8 characters", max = 20)
    @NotEmpty(message = "*Please provide your phone number.")
    private String phone;

    public Address(String address1,
                   String address2,
                   String city,
                   String zip_code,
                   String country,
                   String state,
                   String phone) {
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.zip_code = zip_code;
        this.country = country;
        this.state = state;
        this.phone = phone;
    }
}
