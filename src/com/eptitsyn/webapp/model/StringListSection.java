package com.eptitsyn.webapp.model;

import java.util.List;

public class StringListSection extends AbstractSection {
    private List<String> list;

    public StringListSection(List<String> list) {
        this.list = list;
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
