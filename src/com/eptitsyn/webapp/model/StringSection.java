package com.eptitsyn.webapp.model;

import java.util.Objects;

public class StringSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private String text;

    public StringSection() {
    }

    public StringSection(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringSection that = (StringSection) o;
        return Objects.equals(text, that.text);
    }

    @Override
    public String toString() {
        return text + '\n';
    }
}
