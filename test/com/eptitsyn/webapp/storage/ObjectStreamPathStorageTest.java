package com.eptitsyn.webapp.storage;

public class ObjectStreamPathStorageTest extends AbstractFilesystemStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR));
    }
}
