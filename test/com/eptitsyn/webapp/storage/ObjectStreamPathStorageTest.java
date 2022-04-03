package com.eptitsyn.webapp.storage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObjectStreamPathStorageTest extends AbstractPathStorageTest {
    public ObjectStreamPathStorageTest() {
        super(new ObjectStreamPathStorage(STORAGE_DIR));
    }

    @Override
    @Test
    void update() {
        storage.update(expectedResumes.get(3));
        assertEquals(expectedResumes.get(3), storage.get(expectedResumes.get(3).getUuid()));
    }
}
