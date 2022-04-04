package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.storage.serializer.DataStreamSerializer;

public class DataStreamPathStorageTest extends AbstractFilesystemStorageTest {
    public DataStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR, new DataStreamSerializer()));
    }
}
