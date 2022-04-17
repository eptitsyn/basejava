package com.eptitsyn.webapp.storage;

import static com.eptitsyn.webapp.util.SqlUtil.executeQuery;
import static com.eptitsyn.webapp.util.SqlUtil.executeQuery;

import com.eptitsyn.webapp.exception.ExistStorageException;
import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.sql.ConnectionFactory;
import com.eptitsyn.webapp.util.SqlUtil;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SqlStorage implements Storage {

    public final ConnectionFactory connectionFactory;

    public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
        this.connectionFactory = () -> DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    }

    @Override
    public void clear() {
        executeQuery(connectionFactory, "DELETE FROM resume");
    }

    @Override
    public void update(Resume r) {
        if (executeQuery(connectionFactory, "UPDATE resume SET full_name=? WHERE uuid=?",
            (rs, cnt) -> {
                return cnt;
            }, r.getFullName(), r.getUuid()) != 1) {
            throw new NotExistStorageException(r.getUuid());
        }
        ;
    }

    @Override
    public void save(Resume r) {
        try {
            executeQuery(connectionFactory, "INSERT INTO resume (uuid, full_name) VALUES (?,?)",
                (rs, cnt) -> {
                    return cnt;
                }, r.getUuid(), r.getFullName());
        } catch (StorageException e) {
            if (e.getMessage().contains("duplicate")) {
                throw new ExistStorageException(r.getUuid());
            }
            throw e;
        }
    }

    @Override
    public Resume get(String uuid) {
        Resume result = executeQuery(connectionFactory, "SELECT * FROM resume WHERE uuid=?",
            (resultSet, cnt) -> {
                try {
                    if (!resultSet.next()) {
                        throw new NotExistStorageException(uuid);
                    }
                    return new Resume(resultSet.getString("uuid"),
                        resultSet.getString("full_name"));
                } catch (SQLException e) {
                    throw new StorageException(e);
                }
            }, uuid);
        return result;
    }

    @Override
    public void delete(String uuid) {
        if (executeQuery(connectionFactory, "DELETE FROM resume WHERE uuid=?", (rs, cnt) -> {
            return cnt == 0;
        }, uuid)) {
            throw new NotExistStorageException(uuid);
        }
        ;
    }

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = executeQuery(connectionFactory,
            "SELECT * FROM resume ORDER BY full_name",
            (resultSet, cnt) -> {
                try {
                    List<Resume> resultIn = new ArrayList<>();
                    while (resultSet.next()) {
                        resultIn.add(new Resume(resultSet.getString("uuid"),
                            resultSet.getString("full_name")));
                    }
                    return resultIn;
                } catch (SQLException e) {
                    throw new StorageException(e);
                }
            });
        return result;
    }

    @Override
    public int size() {
        int result = executeQuery(connectionFactory, "SELECT COUNT(*) FROM resume",
            (resultSet, cnt) -> {
                try {
                    resultSet.next();
                    return resultSet.getInt(1);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        return result;
    }
}
