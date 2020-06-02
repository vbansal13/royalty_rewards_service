package com.vbansal13.identity_service.payload.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MembershipRequest {

    @JsonProperty("merchant_id")
    private Long merchantId;
}
