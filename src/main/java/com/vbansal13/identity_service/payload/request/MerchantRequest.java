package com.vbansal13.identity_service.payload.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vbansal13.identity_service.models.Address;
import com.vbansal13.identity_service.models.RewardType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MerchantRequest {


    @NotBlank
    @JsonProperty("business_name")
    private String businessName;

    @JsonProperty("reward_type")
    private RewardType rewardType;


    @JsonProperty("reward_orders_required")
    private int rewardOrdersRequired;

    @JsonProperty("reward_discount")
    private int rewardDiscount;

    private Address address;

}
