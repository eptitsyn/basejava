package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.sql.ConnectionFactory;
import com.eptitsyn.webapp.sql.SqlUtil;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {


    private final ConnectionFactory connectionFactory;
    private final SqlUtil sqlUtil;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            throw new StorageException(e);
        }
        connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        sqlUtil = new SqlUtil(connectionFactory);
    }

    @Override
    public void clear() {
        sqlUtil.executeQuery("DELETE FROM resume", PreparedStatement::executeUpdate);
    }

    @Override
    public void update(Resume r) {
        sqlUtil.executeQuery("UPDATE resume SET full_name=? WHERE uuid=?", ps -> {
            try {
                ps.setObject(1, r.getFullName());
                ps.setObject(2, r.getUuid());
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(r.getUuid());
                }
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        });
    }

    @Override
    public void save(Resume r) {
        sqlUtil.executeQuery("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setObject(1, r.getUuid());
            ps.setObject(2, r.getFullName());
            ps.executeUpdate();
        });
    }

    @Override
    public Resume get(String uuid) {
        final Resume[] result = new Resume[1];
        sqlUtil.executeQuery("SELECT * FROM resume WHERE uuid=?", ps -> {
            ps.setObject(1, uuid);
            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            result[0] = new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"));
        });
        return result[0];
    }

    @Override
    public void delete(String uuid) {
        sqlUtil.executeQuery("DELETE FROM resume WHERE uuid=?",
            ps -> {
                ps.setObject(1, uuid);
                if (ps.executeUpdate() != 1) {
                    throw new NotExistStorageException(uuid);
                }
            });
    }


    @Override
    public List<Resume> getAllSorted() {
        List<Resume> resumes = new ArrayList<>();
        sqlUtil.executeQuery("SELECT * FROM resume ORDER BY full_name", ps -> {
            ResultSet resultSet = ps.executeQuery();
            while (resultSet.next()) {
                resumes.add(
                    new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
        });
        return resumes;
    }

    @Override
    public int size() {
        final int[] result = new int[1];
        sqlUtil.executeQuery("SELECT COUNT(*) FROM resume", preparedStatement -> {
            if (preparedStatement.execute()) {
                ResultSet rs = preparedStatement.getResultSet();
                rs.next();
                result[0] = rs.getInt(1);
            } else {
                result[0] = -1;
            }
        });
        return result[0];
    }
}
