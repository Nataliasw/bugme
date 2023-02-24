package com.finalproject.bugme.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public enum Type {
   EPIC,BUG,STORY,TASK,SUBTASK;

    @JsonCreator
    public static State fromString(@JsonProperty("type") String value) {
        return State.valueOf(value);
    }

}
