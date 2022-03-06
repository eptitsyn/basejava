package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.ExistStorageException;
import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {
    protected static final int EXPECTED_SIZE = 5;
    protected Storage storage;
    protected Resume[] expectedResumes = new Resume[EXPECTED_SIZE];

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        for (int i = 0; i < EXPECTED_SIZE; i++) {
            Resume r = new Resume();
            storage.save(r);
            expectedResumes[i] = r;
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
        storage.update(expectedResumes[3]);
        assertSame(expectedResumes[3], storage.get(expectedResumes[3].getUuid()));
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
        assertEquals(r, storage.get(r.getUuid()));
    }

    @Test
    void saveExists() {
        assertThrows(ExistStorageException.class, () -> storage.save(expectedResumes[0]));
    }



    @Test
    void get() {
        assertEquals(expectedResumes[3], storage.get(expectedResumes[3].getUuid()));
    }

    @Test
    void getNotExist() {
        assertThrows(StorageException.class, () -> storage.get(new Resume().getUuid()));
    }

    @Test
    void delete() {
        storage.delete(expectedResumes[3].getUuid());
        assertThrows(StorageException.class, () -> storage.get(expectedResumes[3].getUuid()));
        assertEquals(storage.size(), EXPECTED_SIZE - 1);
    }

    @Test
    void deleteNotExisting() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(new Resume().getUuid()));
    }

    @Test
    void getAll() {
        Resume[] actual = storage.getAll();
        assertEquals(actual.length, EXPECTED_SIZE);

        Resume[] expected = Arrays.copyOf(expectedResumes, EXPECTED_SIZE);
        assertArrayEquals(actual, expected);
    }
}