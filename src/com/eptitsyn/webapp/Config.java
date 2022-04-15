package com.eptitsyn.webapp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.Properties;

public class Config {

    protected static final File PROPS = new File("./config/resumes.properties");
    private static final Config INSTANCE = new Config();

    private final Properties props = new Properties();
    private final String storageDir;

    private Config() {
        try (InputStream is = Files.newInputStream(PROPS.toPath())) {
            props.load(is);
            storageDir = props.getProperty("storage.dir");
        } catch (IOException e) {
            throw new IllegalStateException("Invalid config file " + PROPS.getAbsolutePath());
        }
    }

    public static Config get() {
        return INSTANCE;
    }

    public String getStorageDir() {
        return storageDir;
    }
}
