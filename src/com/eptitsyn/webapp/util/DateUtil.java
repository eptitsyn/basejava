package com.eptitsyn.webapp.util;

import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;

public class DateUtil {

  public static final LocalDate NOW = LocalDate.MAX;

  private DateUtil() {
  }

  public static LocalDate of(int year, Month month) {
    return LocalDate.of(year, month, 1);
  }

  public static LocalDate parse(String date) {
    if (date != null && !date.isEmpty()) {
      return LocalDate.parse(date);
    }
    return LocalDate.MIN;
  }

  public static String dateToString(LocalDate date) {
    if (date.isAfter(LocalDate.now().minusDays(1))) {
      return "Сейчас";
    }
    return date.format(DateTimeFormatter.ofPattern("yyyy-MM"));
  }
}
