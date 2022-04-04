package com.eptitsyn.webapp.storage;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractFilesystemStorageTest extends AbstractStorageTest {

    public AbstractFilesystemStorageTest(Storage storage) {
        super(storage);
    }

    @Override
    @Test
    void update() {
        storage.update(expectedResumes.get(3));
        assertEquals(expectedResumes.get(3), storage.get(expectedResumes.get(3).getUuid()));
    }
}
