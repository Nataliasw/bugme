package com.finalproject.bugme.allenums;

public enum Type {

    EPIC("Epic"),BUG("Bug"),STORY("Story"),TASK("Task"),SUBTASK("Subtask");

    private String typeString;

    private Type(String typeString){
        this.typeString = typeString;
    }

    public String getTypeString() {
        return typeString;
    }
}
