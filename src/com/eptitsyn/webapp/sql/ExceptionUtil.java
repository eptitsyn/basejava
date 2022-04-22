package com.eptitsyn.webapp.sql;

import com.eptitsyn.webapp.exception.ExistStorageException;
import com.eptitsyn.webapp.exception.StorageException;
import java.sql.SQLException;
import org.postgresql.util.PSQLException;

public class ExceptionUtil {

  private ExceptionUtil() {
  }

  public static StorageException convertException(SQLException e) {
    if (e instanceof PSQLException && "23505".equals(e.getSQLState())) {
      return new ExistStorageException(null);
    }
    return new StorageException(e);
  }
}
