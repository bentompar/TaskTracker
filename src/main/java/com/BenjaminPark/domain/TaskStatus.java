package com.BenjaminPark.domain;

public enum TaskStatus {
    OPEN("open"),
    IN_PROGRESS("in progress"),
    COMPLETED("completed");

    private final String label;

    TaskStatus(String label) {
        this.label = label;
    }

    /**
     * Returns the label of this taskstatus.
     */
    public String getLabel() {
        return label;
    }
}
