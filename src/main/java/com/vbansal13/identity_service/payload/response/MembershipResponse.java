package com.vbansal13.identity_service.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.vbansal13.identity_service.models.Merchant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MembershipResponse {

    @JsonProperty("membership_id")
    private Long membershipId;


    private Merchant merchant;

    public MembershipResponse(Long membershipId, Merchant merchant) {
        this.membershipId = membershipId;
        this.merchant = merchant;
    }
}
