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
        if (sqlUtil.executeQuery("UPDATE resume SET full_name=? WHERE uuid=?", ps -> {
            try {
                ps.setObject(1, r.getFullName());
                ps.setObject(2, r.getUuid());
                return ps.executeUpdate();
            } catch (SQLException e) {
                throw new StorageException(e);
            }
        }) != 1) {
            throw new NotExistStorageException(r.getUuid());
        }
    }

    @Override
    public void save(Resume r) {
        sqlUtil.executeQuery("INSERT INTO resume (uuid, full_name) VALUES (?,?)", ps -> {
            ps.setObject(1, r.getUuid());
            ps.setObject(2, r.getFullName());
            return ps.executeUpdate();
        });
    }

    @Override
    public Resume get(String uuid) {
        return sqlUtil.executeQuery("SELECT * FROM resume WHERE uuid=?", ps -> {
            ps.setObject(1, uuid);
            ResultSet resultSet = ps.executeQuery();
            if (!resultSet.next()) {
                throw new NotExistStorageException(uuid);
            }
            return new Resume(resultSet.getString("uuid"), resultSet.getString("full_name"));
        });
    }

    @Override
    public void delete(String uuid) {
        if (sqlUtil.executeQuery("DELETE FROM resume WHERE uuid=?",
            ps -> {
                ps.setObject(1, uuid);
                return ps.executeUpdate();
            }) != 1) {
            throw new NotExistStorageException(uuid);
        }
    }

    @Override
    public List<Resume> getAllSorted() {
        return sqlUtil.executeQuery("SELECT * FROM resume ORDER BY full_name", ps -> {
            ResultSet resultSet = ps.executeQuery();
            List<Resume> resumes = new ArrayList<>();
            while (resultSet.next()) {
                resumes.add(
                    new Resume(resultSet.getString("uuid"), resultSet.getString("full_name")));
            }
            return resumes;
        });
    }

    @Override
    public int size() {
        return sqlUtil.executeQuery("SELECT COUNT(*) FROM resume", preparedStatement -> {
            if (preparedStatement.execute()) {
                ResultSet rs = preparedStatement.getResultSet();
                rs.next();
                return rs.getInt(1);
            }
            return 0;
        });
    }
}
