package com.taskmanager.domain;

public enum TaskStatus {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    IN_REVIEW("In Review"),
    DONE("Done");
    
    private final String label;
    TaskStatus(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return label;
    }
}
