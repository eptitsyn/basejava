package com.eptitsyn.webapp.sql;

import com.eptitsyn.webapp.exception.StorageException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SqlUtil {

    private final ConnectionFactory connectionFactory;

    public SqlUtil(ConnectionFactory cf) {
        connectionFactory = cf;
    }

    public <T> T executeQuery(String sql,
        SqlFunction<PreparedStatement, T> handler, Object... id) {
        try (Connection connection = connectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < id.length; i++) {
                ps.setObject(i + 1, id[i]);
            }
            return handler.apply(ps);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }
}
