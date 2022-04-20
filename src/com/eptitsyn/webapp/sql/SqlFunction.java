package com.eptitsyn.webapp.sql;

import java.sql.SQLException;

@FunctionalInterface
public interface SqlFunction<T, R> {

    R apply(T t) throws SQLException;
}
