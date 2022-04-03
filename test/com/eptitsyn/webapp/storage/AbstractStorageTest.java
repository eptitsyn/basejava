package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.ExistStorageException;
import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.eptitsyn.webapp.ResumeTestData.generateTestResume;
import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractStorageTest {
    protected static final String STORAGE_DIR = "/Users/ep/Documents/dev/java-ops/basejava/storage";

    protected static final int EXPECTED_SIZE = 5;
    protected final Storage storage;
    protected final List<Resume> expectedResumes = new ArrayList<>();

    public AbstractStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() throws MalformedURLException {
        storage.clear();
        for (int i = 0; i < EXPECTED_SIZE; i++) {
            Resume r = generateTestResume(UUID.randomUUID().toString(), "John Doe");
            storage.save(r);
            expectedResumes.add(r);
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
        storage.update(expectedResumes.get(3));
        assertSame(expectedResumes.get(3), storage.get(expectedResumes.get(3).getUuid()));
    }

    @Test
    void updateNotExisting() {
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume("John Doe")));
    }

    @Test
    void save() {
        Resume r = new Resume("John Doe");
        storage.save(r);
        assertEquals(EXPECTED_SIZE + 1, storage.size());
        assertEquals(r, storage.get(r.getUuid()));
    }

    @Test
    void saveExists() {
        assertThrows(ExistStorageException.class, () -> storage.save(expectedResumes.get(0)));
    }


    @Test
    void get() {
        Resume expected = expectedResumes.get(3);
        Resume actual = storage.get(expected.getUuid());
        assertEquals(expected, actual);
    }

    @Test
    void getNotExist() {
        assertThrows(StorageException.class, () -> storage.get(UUID.randomUUID().toString()));
    }

    @Test
    void delete() {
        storage.delete(expectedResumes.get(3).getUuid());
        assertThrows(StorageException.class, () -> storage.get(expectedResumes.get(3).getUuid()));
        assertEquals(EXPECTED_SIZE - 1, storage.size());
    }

    @Test
    void deleteNotExisting() {
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID.randomUUID().toString()));
    }

    @Test
    void getAllSorted() {
        List<Resume> actual = storage.getAllSorted();
        assertEquals(EXPECTED_SIZE, actual.size());

        List<Resume> expected = expectedResumes;
        expected.sort(AbstractStorage.RESUME_NAME_UUID_COMPARATOR);

        assertEquals(expected, actual);
    }
}