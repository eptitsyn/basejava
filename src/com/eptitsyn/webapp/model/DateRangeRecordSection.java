package com.eptitsyn.webapp.model;

import java.util.List;

public class DateRangeRecordSection extends AbstractSection {
    List<DateRangeRecord> list;

    public DateRangeRecordSection(List<DateRangeRecord> list) {
        this.list = list;
    }

    public void addRecord(DateRangeRecord record) {
        list.add(record);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (DateRangeRecord r : list) {
            stringBuilder.append(r.toString());
        }
        return stringBuilder.toString();
    }
}

