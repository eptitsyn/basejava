package com.eptitsyn.webapp.storage;

class ObjectStreamFileStorageTest extends AbstractFilesystemStorageTest {
    public ObjectStreamFileStorageTest() {
        super(new ObjectStreamFileStorage(STORAGE_DIR));
    }
}