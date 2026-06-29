package com.taskmanager.domain;

public enum Priority {
    LOW("Low"), 
    MEDIUM("Medium"),
    HIGH("High"),
    CRITICAL("Critical");
    
    private final String label;
    Priority(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
