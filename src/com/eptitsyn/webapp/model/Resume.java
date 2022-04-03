package com.eptitsyn.webapp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.EnumMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

/**
 * Initial resume class
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Resume implements Comparable<Resume>, Serializable {
    private static final long serialVersionUID = 1L;

    // Unique identifier
    private String uuid;
    private String fullName;
    private final Map<SectionType, AbstractSection> sections = new EnumMap<>(SectionType.class);
    private final Map<ContactType, String> contacts = new EnumMap<>(ContactType.class);

    public Resume() {}

    public Resume(String fullName) {
        this(UUID.randomUUID().toString(), fullName);
    }

    public Resume(String uuid, String fullName) {
        this.uuid = uuid;
        this.fullName = fullName;
    }

    public String getContact(ContactType type) {
        return contacts.get(type);
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public AbstractSection getSection(SectionType type) {
        return sections.get(type);
    }

    public void putContacts(ContactType type, String contact) {
        contacts.put(type, contact);
    }

    public void putSection(SectionType type, AbstractSection section) {
        sections.put(type, section);
    }

    @Override
    public int compareTo(Resume o) {
        return uuid.compareTo(o.getUuid());
    }

    public String getUuid() {
        return uuid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Resume resume = (Resume) o;
        return uuid.equals(resume.uuid) && Objects.equals(fullName, resume.fullName) && Objects.equals(sections, resume.sections) && Objects.equals(contacts, resume.contacts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, fullName, sections, contacts);
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
            builder.append(key.toString()).append("\n");
            builder.append(value.toString()).append("\n");
        });
        return builder.toString();
    }
}
