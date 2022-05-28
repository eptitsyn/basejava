package com.eptitsyn.webapp;

import com.eptitsyn.webapp.storage.SqlStorage;
import com.eptitsyn.webapp.storage.Storage;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {

  protected static final String PROPERTIES = "/resumes.properties";
  private static final Config INSTANCE = new Config();

  private final Storage sqlStorage;
  private final Properties props = new Properties();
  private final String storageDir;

  private Config() {
    try (InputStream is = Config.class.getResourceAsStream(PROPERTIES)) {
      props.load(is);
      storageDir = props.getProperty("storage.dir");
    } catch (IOException e) {
      throw new IllegalStateException("Invalid config file " + PROPERTIES);
    }
    sqlStorage = new SqlStorage(props.getProperty("db.url"), props.getProperty("db.user"),
            props.getProperty("db.password"));
  }

  public static Config get() {
    return INSTANCE;
  }

  public String getDbPassword() {
    return props.getProperty("db.password");
  }

  public String getDbUrl() {
    return props.getProperty("db.url");
  }

  public String getDbUser() {
    return props.getProperty("db.user");
  }

  public Storage getSqlStorage() {
    return sqlStorage;
  }

  public String getStorageDir() {
    return storageDir;
  }

}
