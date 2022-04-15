package com.eptitsyn.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    EMAIL("E-mail"),
    SKYPE("Skype"),
    GITHUB("GitHub"),
    OTHER("Др.");

    private final String description;

    ContactType(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
}
