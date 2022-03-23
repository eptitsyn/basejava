package com.eptitsyn.webapp.model;

import java.util.*;

/**
 * Initial resume class
 */
public class Resume implements Comparable<Resume> {

    // Unique identifier
    private final String uuid;
    private String fullName;
    private Map<SectionType, AbstractSection> sections = new HashMap<>();

    private Map<ContactType, String> contacts = new HashMap<>();

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public AbstractSection getSection(SectionType type) {
        return sections.get(type);
    }

    public void putSection(SectionType type, AbstractSection section) {
        sections.put(type, section);
    }

    public List<AbstractSection> getAllSections() {
        return sections.values().stream().toList();
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public void putContacts(ContactType type, String contact) {
        contacts.put(type, contact);
    }

    @Override
    public int hashCode() {
        return uuid.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid);
    }

    @Override
    public String toString() {
        return uuid + "\n\n"
                + fullName + "\n\n"
                + contactsToString()
                + sectionsToString();
    }

    private String contactsToString() {
        StringBuilder builder = new StringBuilder();

        contacts.forEach((key, value) -> builder.append(key.toString()).append(" : ").append(value).append("\n"));
        return builder.toString();
    }

    private String sectionsToString() {
        StringBuilder builder = new StringBuilder();

        sections.forEach((key, value) -> {
            builder.append("\n<h2>").append(key.toString()).append("</h2>\n");
            builder.append(value.toString());
        });
        return builder.toString();
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.getUuid());
    }
}
