package com.eptitsyn.webapp.model;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Experience extends AbstractSection {

    private static final long serialVersionUID = 1L;

    private List<Organisation> organisations;

    public Experience() {
    }

    public Experience(Organisation... organisations) {
        this(Arrays.asList(organisations));
    }

    public Experience(List<Organisation> organisations) {
        Objects.requireNonNull(organisations, "Organisation list can't br null");
        this.organisations = organisations;
    }

    public List<Organisation> getOrganisations() {
        return organisations;
    }

    public void addOrganisation(Organisation record) {
        organisations.add(record);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Experience that = (Experience) o;
        return organisations.equals(that.organisations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(organisations);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Organisation organisation : organisations) {
            stringBuilder.append(organisation.toString()).append('\n');
        }
        return stringBuilder.toString();
    }
}

