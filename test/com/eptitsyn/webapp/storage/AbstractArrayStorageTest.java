package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.ExistStorageException;
import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static com.eptitsyn.webapp.storage.AbstractArrayStorage.STORAGE_LIMIT;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

abstract class AbstractArrayStorageTest {
    protected static final int EXPECTED_SIZE = 5;
    protected Storage storage;
    protected Resume[] testResume = new Resume[EXPECTED_SIZE];

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        for (int i = 0; i < EXPECTED_SIZE; i++) {
            Resume r = new Resume();
            storage.save(r);
            testResume[i] = r;
        }
    }

    @Test
    void clear() {
        assertEquals(EXPECTED_SIZE, storage.size());
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void update() {
        storage.update(testResume[3]);
        assertSame(testResume[3], storage.get(testResume[3].getUuid()));
    }

    @Test
    void updateNotExisting() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume()));
    }

    @Test
    void save() {
        Resume r = new Resume();
        storage.save(r);
        assertEquals(EXPECTED_SIZE + 1, storage.size());
        assertNotNull(storage.get(r.getUuid()));
    }

    @Test
    void saveExists() {
        Resume r = new Resume();
        storage.save(r);
        assertThrows(ExistStorageException.class, () -> storage.save(r));
    }

    @Test
    void saveLimit() {
        assertDoesNotThrow(() -> {
            for (int i = 0; i < (STORAGE_LIMIT - EXPECTED_SIZE); i++) {
                storage.save(new Resume());
            }
        });
        assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

    @Test
    void get() {
        assertSame(storage.get(testResume[3].getUuid()), testResume[3]);
    }

    @Test
    void getNotExist() {
        assertThrows(StorageException.class, () -> storage.get(new Resume().getUuid()));
    }

    @Test
    void delete() {
        storage.delete(testResume[3].getUuid());
        assertThrows(NotExistStorageException.class, () -> storage.delete(testResume[3].getUuid()));
    }

    @Test
    void deleteNotExisting() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(new Resume().getUuid()));
    }

    @Test
    void getAll() {
        Resume[] resumes = storage.getAll();
        assertEquals(resumes.length, EXPECTED_SIZE);

        Resume[] sorted = Arrays.copyOf(testResume, EXPECTED_SIZE);
        Arrays.sort(resumes);
        Arrays.sort(sorted);
        assertEquals(Arrays.compare(resumes, sorted), 0);
    }

    @Test
    void size() {
        assertEquals(EXPECTED_SIZE, storage.size());
        storage.save(new Resume());
        assertEquals(EXPECTED_SIZE + 1, storage.size());
        storage.save(new Resume());
        assertEquals(EXPECTED_SIZE + 2, storage.size());
    }
}