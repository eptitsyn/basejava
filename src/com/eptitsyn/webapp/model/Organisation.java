package com.eptitsyn.webapp.model;

import java.net.URL;
import java.time.LocalDate;

public class Organisation {
    private LocalDate startDate;
    private LocalDate endDate;
    private String title;
    private String description;
    private URL website;

    public Organisation(LocalDate startDate, LocalDate endDate, String title, String description, URL website) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.title = title;
        this.description = description;
        this.website = website;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public URL getWebsite() {
        return website;
    }

    public void setWebsite(URL website) {
        this.website = website;
    }

    @Override
    public String toString() {
        return startDate + " - " + endDate + '\n' +
                " " + title + " " + website + '\n' +
                " " + description + '\n';
    }
}
