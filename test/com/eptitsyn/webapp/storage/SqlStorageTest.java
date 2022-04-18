package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.Config;

class SqlStorageTest extends AbstractStorageTest{

    public SqlStorageTest() {
        super(Config.get().getSqlStorage());
    }
}