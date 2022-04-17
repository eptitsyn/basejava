package com.eptitsyn.webapp.util;

import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.sql.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.BiFunction;

public class SqlUtil {

    public static <T> T executeQuery(ConnectionFactory connectionFactory, String sql,
        BiFunction<ResultSet, Integer, T> handler, Object... id) {
        try (Connection connection = connectionFactory.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql)) {
            for (int i = 0; i < id.length; i++) {
                ps.setObject(i + 1, id[i]);
            }
            boolean e = ps.execute();
            ResultSet rs = ps.getResultSet();
            int updateCount = ps.getUpdateCount();
            return handler.apply(rs, updateCount);
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    public static void executeQuery(ConnectionFactory connectionFactory, String sql, Object... id) {
        executeQuery(connectionFactory, sql, (rs, cnt) -> {
            return null;
        }, id);
    }
}
