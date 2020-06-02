package com.vbansal13.identity_service.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter

public enum RewardType {

    FREE_MEAL("free_meal"),
    DISCOUNT("discount"),
    NONE("none");

    private final String rewardType;


    RewardType(String rewardType) {
        this.rewardType = rewardType;
    }

    @JsonCreator
    public static RewardType forValue(String name)
    {
        for (RewardType type : RewardType.values()) {
            if (type.rewardType.equalsIgnoreCase(name)) {
                return type;
            }
        }
        return NONE;
    }
}
