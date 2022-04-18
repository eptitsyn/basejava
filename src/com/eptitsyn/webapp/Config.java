package com.eptitsyn.webapp;

import com.eptitsyn.webapp.storage.SqlStorage;
import com.eptitsyn.webapp.storage.Storage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

public class Config {

    protected static final File PROPERTIES = new File("./config/resumes.properties");
    private static final Config INSTANCE = new Config();

    private final Storage sqlStorage;
    private final Properties props = new Properties();
    private final String storageDir;
    private final String dbUrl;
    private final String dbUser;
    private final String dbPassword;

    private Config() {
        try (InputStream is = Files.newInputStream(PROPERTIES.toPath())) {
            props.load(is);
            storageDir = props.getProperty("storage.dir");
            dbUrl = props.getProperty("db.url");
            dbUser = props.getProperty("db.user");
            dbPassword = props.getProperty("db.password");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPERTIES.getAbsolutePath());
        }
        sqlStorage = new SqlStorage(dbUrl, dbUser, dbPassword);
    }

    public static Config get() {
        return INSTANCE;
    }

    public String getStorageDir() {
        return storageDir;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public String getDbUser() {
        return dbUser;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public Storage getSqlStorage() {
        return sqlStorage;
    }
}
