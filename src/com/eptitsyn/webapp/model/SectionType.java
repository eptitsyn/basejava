package com.eptitsyn.webapp.model;

public enum SectionType {
    ACHIEVEMENTS("Достижения"),
    EDUCATION("Образование"),
    EXPERIENCE("Опыт"),
    OBJECTIVE("Цель"),
    PERSONAL("Личные качества"),
    QUALIFICATIONS("Квалификация");

    private final String fieldDescription;

    SectionType(String fieldDescription) {
        this.fieldDescription = fieldDescription;
    }

    @Override
    public String toString() {
        return fieldDescription;
    }
}
