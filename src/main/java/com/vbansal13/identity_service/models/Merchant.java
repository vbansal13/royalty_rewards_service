package com.vbansal13.identity_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "merchants")
public class Merchant {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "merchant_id")
    @JsonProperty("merchant_id")
    private Long id;

    @Column(name = "name")
    @JsonProperty("business_name")
    @NotEmpty(message = "*Please specify business name.")
    private String name;

    @JsonIgnore
    @Column(name = "user_email")
    private String userEmail;

    @Embedded
    private Address address;

    @JsonIgnore
    @Column(name = "verified")
    private Boolean verified;

    //@OneToMany(mappedBy = "merchantId")
    //private Set<Membership> membership;

    @Column(name = "reward_type")
    private RewardType rewardType;

    @Column(name = "reward_orders_required")
    private int rewardOrdersRequired;

    @Column(name = "reward_discount")
    private int rewardDiscount;


    public Merchant(String name,
                    String userEmail,
                    Address address,
                    RewardType rewardType,
                    int rewardOrdersRequired,
                    int rewardDiscount) {
        this.name = name;
        this.userEmail = userEmail;
        this.address = address;
        this.rewardType = rewardType;
        this.rewardOrdersRequired = rewardOrdersRequired;
        this.rewardDiscount = 0;
        if (rewardType == RewardType.DISCOUNT) {
            this.rewardDiscount = rewardDiscount;
        }
        this.verified = false;
    }

    @Override
    public String toString() {
        return "Merchant{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", address=" + address +
                ", verified=" + verified +
                ", rewardType=" + rewardType +
                ", rewardOrdersRequired=" + rewardOrdersRequired +
                ", rewardDiscount=" + rewardDiscount +
                '}';
    }


}

