package com.eptitsyn.webapp.util;

import java.time.LocalDate;
import java.time.Month;

public class DateUtil {

  public static final LocalDate NOW = LocalDate.MAX;

  public static LocalDate of(int year, Month month) {
    return LocalDate.of(year, month, 1);
  }

  public static LocalDate parse(String dateString) {
    if (dateString != null && !dateString.isEmpty()) {
      return LocalDate.parse(dateString);
    }
    return LocalDate.MIN;
  }
}
