package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;
import org.junit.jupiter.api.Test;

import static com.eptitsyn.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

abstract class AbstractArrayStorageTest extends AbstractStorageTest {

    public AbstractArrayStorageTest(Storage storage) {
        super(storage);
    }

    @Test
    void saveOverflow() {
        assertDoesNotThrow(() -> {
            for (int i = 0; i < (STORAGE_LIMIT - EXPECTED_SIZE); i++) {
                storage.save(new Resume());
            }
        });
        assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }
}