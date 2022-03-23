package com.eptitsyn.webapp.model;

public enum ContactType {
    PHONE("Тел."),
    EMAIL("e-mail"),
    SKYPE("Skype"),
    GITHUB("GitHub"),
    OTHER("Др.");

    private final String fieldDescription;

    ContactType(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    @Override
    public String toString() {
        return fieldDescription;
    }
}
