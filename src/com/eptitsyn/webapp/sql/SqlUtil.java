package com.eptitsyn.webapp.sql;

import com.eptitsyn.webapp.exception.StorageException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.postgresql.util.PSQLException;

public class SqlUtil {

  private final ConnectionFactory connectionFactory;

  public SqlUtil(ConnectionFactory cf) {
    connectionFactory = cf;
  }

  public <T> T executeQuery(String sql, SqlFunction<PreparedStatement, T> handler) {
    try (Connection connection = connectionFactory.getConnection();
        PreparedStatement ps = connection.prepareStatement(sql)) {
      return handler.apply(ps);
    } catch (PSQLException e) {
      throw ExceptionUtil.convertException(e);
    } catch (SQLException e) {
      throw new StorageException(e);
    }
  }

  public <T> T transactionalExecute(SqlTransaction<T> executor) {
    try (Connection conn = connectionFactory.getConnection()) {
      try {
        conn.setAutoCommit(false);
        T res = executor.execute(conn);
        conn.commit();
        return res;
      } catch (SQLException e) {
        conn.rollback();
        throw ExceptionUtil.convertException(e);
      }
    } catch (SQLException e) {
      throw new StorageException(e);
    }
  }
}
