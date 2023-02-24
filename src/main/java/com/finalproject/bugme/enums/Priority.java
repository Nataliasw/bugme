package com.finalproject.bugme.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum Priority {
HIGH,MEDIUM,LOW;

    @JsonCreator
    public static State fromString(@JsonProperty("priority") String value) {
        return State.valueOf(value);
    }
}
