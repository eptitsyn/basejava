package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.sql.ConnectionFactory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class SqlStorage implements Storage {

    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        try (Connection conn = connectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement("DELETE FROM resume")) {
            ps.execute();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
    }

    @Override
    public void update(Resume r) {

    }

    @Override
    public void save(Resume r) {

    }

    @Override
    public Resume get(String uuid) {
        try (Connection conn = connectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM resume r WHERE uuid=?")) {
            ps.setString(1, uuid);
            ResultSet rs = ps.executeQuery();
        } catch (SQLException e) {
            throw new StorageException(e);
        }
        return null;
    }

    @Override
    public void delete(String uuid) {

    }

    @Override
    public List<Resume> getAllSorted() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }
}
