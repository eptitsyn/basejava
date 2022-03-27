package com.eptitsyn.webapp.model;

import com.sun.istack.internal.NotNull;

import java.util.List;
import java.util.Objects;

public class Experience extends AbstractSection {
    private List<Organisation> organisationList;

    public Experience(List<Organisation> organisationList) {
        Objects.requireNonNull(organisationList, "Organisation list can't br null");
        this.organisationList = organisationList;
    }

    public void addOrganisation(Organisation record) {
        organisationList.add(record);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Organisation organisation : organisationList) {
            stringBuilder.append(organisation.toString()).append('\n');
        }
        return stringBuilder.toString();
    }
}

