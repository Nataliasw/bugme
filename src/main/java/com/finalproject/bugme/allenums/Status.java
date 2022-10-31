package com.finalproject.bugme.allenums;

public enum Status {

    OPEN("Open"),IN_PROGRESS("In Progress"),DONE("Done"),TO_DO("To Do"),IN_REVIEW("IN_REVIEW"),
    UNDER_REVIEW("Under Review"),APPROVED("Approved"),CANCELLED("Cancelled"),REJECTED("Rejected"),
    CLOSED("Closed"),RESOLVED("Resolved"),REOPENED("Reopened");

    private String statusString;

    private Status(String statusString){
        this.statusString = statusString;
    }

    public String getStatusString(){
        return statusString;
    }


}
