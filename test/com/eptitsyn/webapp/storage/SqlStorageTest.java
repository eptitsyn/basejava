package com.eptitsyn.webapp.storage;

import static org.junit.jupiter.api.Assertions.*;

import com.eptitsyn.webapp.Config;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SqlStorageTest extends AbstractStorageTest{

    public SqlStorageTest() {
        super(new SqlStorage(Config.get().getDbUrl(), Config.get().getDbUser(), Config.get().getDbPassword()));
    }
}