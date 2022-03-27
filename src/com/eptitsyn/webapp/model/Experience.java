package com.eptitsyn.webapp.model;

import java.util.List;

public class Experience extends AbstractSection {
    private List<Organisation> list;

    public Experience(List<Organisation> list) {
        this.list = list;
    }

    public void addRecord(Organisation record) {
        list.add(record);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Organisation r : list) {
            stringBuilder.append(r.toString());
        }
        return stringBuilder.toString();
    }
}

