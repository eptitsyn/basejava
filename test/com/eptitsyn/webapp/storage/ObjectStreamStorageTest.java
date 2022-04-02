package com.eptitsyn.webapp.storage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectStreamStorageTest extends AbstractFileStorageTest {
    public ObjectStreamStorageTest() {
        super(new ObjectStreamStorage(STORAGE_DIR));
    }

    @Override
    @Test
    void update() {
        storage.update(expectedResumes.get(3));
        assertEquals(expectedResumes.get(3), storage.get(expectedResumes.get(3).getUuid()));
    }
}