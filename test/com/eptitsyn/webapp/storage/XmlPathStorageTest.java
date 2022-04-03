package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.storage.serializer.XmlStreamSerializer;

import static com.eptitsyn.webapp.storage.AbstractStorageTest.STORAGE_DIR;

public class XmlPathStorageTest extends AbstractFilesystemStorageTest {
    public XmlPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR, new XmlStreamSerializer()));
    }
}
