package com.eptitsyn.webapp.sql;

import java.sql.SQLException;

@FunctionalInterface
public interface SqlFunction<T> {

    void apply(T t) throws SQLException;
}
