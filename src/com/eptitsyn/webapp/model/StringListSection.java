package com.eptitsyn.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class StringListSection extends AbstractSection {
    private static final long serialVersionUID = 1L;
    private final List<String> list;

    public StringListSection(String... items) {
        this(Arrays.asList(items));
    }

    public StringListSection(List<String> list) {
        Objects.requireNonNull(list);
        this.list = list;
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringListSection that = (StringListSection) o;
        return Objects.equals(list, that.list);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (String s : list) {
            builder.append(" * ").append(s).append("\n");
        }
        return builder.toString();
    }
}
