package com.eptitsyn.webapp.model;

import java.net.URL;
import java.util.List;

public class Organisation {
    private List<Position> positions;
    private String name;
    private URL website;

    public Organisation(List<Position> positions, String name, URL website) {
        this.positions = positions;
        this.name = name;
        this.website = website;
    }

    public void addPosition(Position position) {
        positions.add(position);
    }

    public List<Position> getPositions() {
        return positions;
    }

    public String getName() {
        return name;
    }

    public URL getWebsite() {
        return website;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Position position : positions) {
            stringBuilder.append(this.name)
                    .append(" ")
                    .append(this.website)
                    .append('\n');
            stringBuilder.append(position.getStartDate())
                    .append(" - ")
                    .append(position.getEndDate()).append("  ")
                    .append(position.getTitle()).append('\n')
                    .append(position.getDescription()).append('\n');
        }
        return stringBuilder.toString();
    }
}
