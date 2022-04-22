package com.eptitsyn.webapp.sql;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface SqlTransaction<T> {

  T execute(Connection conn) throws SQLException;
}