package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.ContactType;
import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.sql.SqlUtil;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class SqlStorage implements Storage {

  private final SqlUtil sqlUtil;

  public SqlStorage(String dbUrl, String dbUser, String dbPassword) {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      throw new StorageException(e);
    }
    sqlUtil = new SqlUtil(() -> DriverManager.getConnection(dbUrl, dbUser, dbPassword));
  }

  @Override
  public void clear() {
    sqlUtil.executeQuery("DELETE FROM resume", PreparedStatement::execute);
  }

  @Override
  public void update(Resume r) {
    sqlUtil.transactionalExecute(conn -> {
      try (PreparedStatement ps = conn.prepareStatement(
          "UPDATE resume SET full_name=? WHERE uuid=?")) {
        ps.setObject(1, r.getFullName());
        ps.setObject(2, r.getUuid());
        if (ps.executeUpdate() != 1) {
          throw new NotExistStorageException(r.getUuid());
        }
      }
      try (PreparedStatement ps = conn.prepareStatement(
          "DELETE FROM contact WHERE resume_uuid=?")) {
        ps.setObject(1, r.getUuid());
        ps.execute();
      }
      saveContacts(r, conn);
      return null;
    });
  }

  @Override
  public void save(Resume r) {
    sqlUtil.transactionalExecute(conn -> {
      saveResume(r, conn);
      saveContacts(r, conn);
      return null;
    });
  }

  @Override
  public Resume get(String uuid) {
    return sqlUtil.executeQuery(
        "SELECT uuid,full_name,type,value FROM resume " +
            "LEFT JOIN contact on uuid=resume_uuid " +
            "WHERE uuid=?",
        ps -> {
          ps.setObject(1, uuid);
          ResultSet rs = ps.executeQuery();
          return getResume(rs, uuid);
        });
  }

  @Override
  public void delete(String uuid) {
    sqlUtil.executeQuery("DELETE FROM resume WHERE uuid=?", ps -> {
      ps.setObject(1, uuid);
      if (ps.executeUpdate() != 1) {
        throw new NotExistStorageException(uuid);
      }
      return null;
    });
  }

  @Override
  public List<Resume> getAllSorted() {
    return sqlUtil.executeQuery("SELECT uuid,full_name,type,value FROM resume "
            + "LEFT JOIN contact on uuid=resume_uuid "
            + "ORDER BY full_name, uuid",
        ps -> {
          ResultSet resultSet = ps.executeQuery();
          return getResumeList(resultSet, null);
        });
  }

  @Override
  public int size() {
    return sqlUtil.executeQuery("SELECT COUNT(*) FROM resume", ps -> {
      ResultSet rs = ps.executeQuery();
      return rs.next() ? rs.getInt(1) : 0;
    });
  }

  private void saveContacts(Resume r, Connection conn) throws SQLException {
    try (PreparedStatement ps = conn.prepareStatement(
        "INSERT INTO contact (resume_uuid, type, value) VALUES (?,?,?)")) {
      for (Map.Entry<ContactType, String> e : r.getContacts().entrySet()) {
        ps.setString(1, r.getUuid());
        ps.setString(2, e.getKey().name());
        ps.setString(3, e.getValue());
        ps.addBatch();
      }
      ps.executeBatch();
    }
  }

  private void saveResume(Resume r, Connection conn) throws SQLException {
    try (PreparedStatement ps = conn.prepareStatement(
        "INSERT INTO resume (uuid, full_name) VALUES (?,?)")) {
      ps.setString(1, r.getUuid());
      ps.setString(2, r.getFullName());
      ps.execute();
    }
  }

  private Resume getResume(ResultSet rs, String uuid) throws SQLException {
    return getResumeList(rs, uuid).get(0);
  }

  private List<Resume> getResumeList(ResultSet rs, String uuid) throws SQLException {
    if (!rs.next()) {
      throw new NotExistStorageException(uuid);
    }
    Map<String, Resume> result = new LinkedHashMap<>();
    do {
      if (result.get(rs.getString("uuid")) == null) {
        result.put(rs.getString("uuid"),
            new Resume(rs.getString("uuid"), rs.getString("full_name")));
      }
      String value = rs.getString("value");
      String type = rs.getString("type");
      if (value != null && type != null) {
        ContactType contactType = ContactType.valueOf(type);
        result.get(rs.getString("uuid")).addContact(contactType, value);
      }
    } while (rs.next());
    return new ArrayList<>(result.values());
  }
}
