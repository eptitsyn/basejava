package com.eptitsyn.webapp.model;

import java.util.List;

public class Experience extends AbstractSection {
    private List<Organisation> organisationList;

    public Experience(List<Organisation> organisationList) {
        this.organisationList = organisationList;
    }

    public void addOrganisation(Organisation record) {
        organisationList.add(record);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Organisation r : organisationList) {
            stringBuilder.append(r.toString());
        }
        return stringBuilder.toString();
    }
}

