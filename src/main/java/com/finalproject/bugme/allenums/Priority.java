package com.finalproject.bugme.allenums;

public enum Priority {

    HIGH("High"),MEDIUM("Medium"),LOW("Low");

    private String priorityString;


    private Priority(String priorityString){
        this.priorityString = priorityString;
    }

    public String getPriorityString(){
        return  priorityString;
    }
}
