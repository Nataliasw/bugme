package com.finalproject.bugme.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum State {

    TODO, IN_PROGRESS, FIXED, CLOSED;

    @JsonCreator
    public static State fromString(@JsonProperty("state") String value) {
        return State.valueOf(value);
    }
}
