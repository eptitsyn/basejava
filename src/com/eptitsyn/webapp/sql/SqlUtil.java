package com.eptitsyn.webapp.sql;

import com.eptitsyn.webapp.exception.ExistStorageException;
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
            if ("23505".equals(e.getSQLState())) {
                throw new ExistStorageException("n/a");
            }
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return null;
    }
}
