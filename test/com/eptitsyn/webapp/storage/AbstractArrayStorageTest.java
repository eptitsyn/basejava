package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.ExistStorageException;
import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

abstract class AbstractArrayStorageTest {
    protected Storage storage;

    protected static final int PRELOADED_RESUMES = 5;
    protected static final String UUID_1 = "uuid1";
    protected static final String UUID_2 = "uuid2";
    protected static final String UUID_3 = "uuid3";
    protected static final String UUID_4 = "uuid4";
    protected static final String UUID_5 = "uuid5";

    public AbstractArrayStorageTest(Storage storage) {
        this.storage = storage;
    }

    @BeforeEach
    void setUp() {
        storage.clear();
        storage.save(new Resume(UUID_1));
        storage.save(new Resume(UUID_2));
        storage.save(new Resume(UUID_3));
        storage.save(new Resume(UUID_4));
        storage.save(new Resume(UUID_5));
    }

    @Test
    void clear() {
        assertEquals(PRELOADED_RESUMES, storage.size());
        storage.clear();
        assertEquals(0, storage.size());
    }

    @Test
    void update() {
        Resume r = new Resume(UUID_4);
        storage.update(r);
        assertEquals(r, storage.get(UUID_4));
        assertThrows(NotExistStorageException.class, () -> storage.update(new Resume()));
    }

    @Test
    void save() {
        Resume r = new Resume();
        storage.save(r);
        assertEquals(PRELOADED_RESUMES+1, storage.size());
        assertNotNull(storage.get(r.getUuid()));
        assertThrows(ExistStorageException.class, () -> storage.save(r));
    }

    @Test
    void saveLimit() throws NoSuchFieldException, IllegalAccessException {
        int limit =AbstractArrayStorage.class
                .getDeclaredField("STORAGE_LIMIT")
                .getInt(storage);
        assertDoesNotThrow(()->{
            for (int i = 0; i < limit-PRELOADED_RESUMES; i++) {
                storage.save(new Resume());
            }
        });
        assertThrows(StorageException.class, () -> storage.save(new Resume()));
    }

    @Test
    void get() {
        assertEquals(storage.get(UUID_4).getUuid(), UUID_4);
    }

    @Test
    void updateGetNotExist() {
        assertThrows(NotExistStorageException.class, () -> storage.get("dummy"));
    }


    @Test
    void delete() {
        storage.delete(UUID_4);
        assertThrows(NotExistStorageException.class, () -> storage.delete(UUID_4));
    }

    @Test
    void getAll() {
        Resume[] resumes = storage.getAll();
        assertEquals(resumes.length, PRELOADED_RESUMES);
    }

    @Test
    void size() {
        assertEquals(PRELOADED_RESUMES, storage.size());
        storage.save(new Resume());
        assertEquals(PRELOADED_RESUMES+1, storage.size());
        storage.save(new Resume());
        assertEquals(PRELOADED_RESUMES+2, storage.size());
    }
}