package com.eptitsyn.webapp.model;

import com.eptitsyn.webapp.util.DateUtil;
import com.eptitsyn.webapp.util.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static com.eptitsyn.webapp.util.DateUtil.NOW;

@XmlAccessorType(XmlAccessType.FIELD)
public class Organisation implements Serializable {
    private String name;
    private URL website;
    private List<Position> positions = new ArrayList<>();

    public Organisation() {
    }

    public Organisation(String name, URL website, Position... positions) {
        this(name, website, Arrays.asList(positions));
    }

    public Organisation(String name, URL website, List<Position> positions) {
        this.positions = positions;
        this.name = name;
        this.website = website;
    }

    public void addPosition(Position position) {
        positions.add(position);
    }

    public String getName() {
        return name;
    }

    public List<Position> getPositions() {
        return positions;
    }

    public URL getWebsite() {
        return website;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, website);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organisation that = (Organisation) o;
        return name.equals(that.name) && Objects.equals(website, that.website);
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

    @XmlAccessorType(XmlAccessType.FIELD)
    public static class Position implements Serializable {
        private static final long serialVersionUID = 1L;

        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate startDate;
        @XmlJavaTypeAdapter(LocalDateAdapter.class)
        private LocalDate endDate;
        private String title;
        private String description;

        public Position() {
        }

        public Position(int startYear, Month startMonth, String title, String description) {
            this(DateUtil.of(startYear, startMonth), NOW, title, description);
        }

        public Position(LocalDate startDate, LocalDate endDate, String title, String description) {
            this.startDate = startDate;
            this.endDate = endDate;
            this.title = title;
            this.description = description;
        }

        public String getDescription() {
            return description;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public String getTitle() {
            return title;
        }

        @Override
        public int hashCode() {
            return Objects.hash(startDate, endDate, title, description);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return Objects.equals(startDate, position.startDate) && Objects.equals(endDate, position.endDate) && Objects.equals(title, position.title) && Objects.equals(description, position.description);
        }

        @Override
        public String toString() {
            return "" + startDate +
                    " - " + endDate +
                    " " + title + '\n' +
                    "" + description + '\n';
        }
    }
}
