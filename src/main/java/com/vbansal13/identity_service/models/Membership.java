package com.vbansal13.identity_service.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "membership")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "membership_id")
    private Long id;

    //@ManyToOne(targetEntity = User.class)
    //@JoinColumn(name = "fk_user_id", nullable = false)
    @Column(name = "user_id")
    private Long userId;

    //@ManyToOne(targetEntity = Merchant.class)
    //@JoinColumn(name = "fk_merchant_id", nullable = false)
    @Column(name = "merchant_id")
    private Long merchantId;

    @Column(name = "user_email")
    private String userEmail;


    @Column(name = "order_count")
    private int currentOrderCount;

    public Membership(String userEmail, Long userId, Long merchantId) {
        this.userEmail = userEmail;
        this.userId = userId;
        this.merchantId = merchantId;
        this.currentOrderCount = 0;
    }

    @Override
    public String toString() {
        return "Membership{" +
                "id=" + id +
                ", userId=" + userId +
                ", merchantId=" + merchantId +
                ", userEmail='" + userEmail + '\'' +
                ", currentOrderCount=" + currentOrderCount +
                '}';
    }
}
