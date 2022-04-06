package com.eptitsyn.webapp.model;

public enum SectionType {
  PERSONAL("Личные качества"),
  OBJECTIVE("Цель"),
  ACHIEVEMENTS("Достижения"),
  QUALIFICATIONS("Квалификация"),
  EXPERIENCE("Опыт"),
  EDUCATION("Образование");

  private final String description;

  SectionType(String description) {
    this.description = description;
  }

  @Override
  public String toString() {
    return description;
  }
}
