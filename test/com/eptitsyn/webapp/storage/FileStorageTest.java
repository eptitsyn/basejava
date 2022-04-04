package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.storage.serializer.ObjectStreamSerializer;

import java.io.File;

class FileStorageTest extends AbstractFilesystemStorageTest {
    public FileStorageTest() {
        super(new FileStorage(new File(STORAGE_DIR), new ObjectStreamSerializer()));
    }
}