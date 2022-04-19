package com.eptitsyn.webapp.storage;

import static com.eptitsyn.webapp.ResumeTestData.generateTestResume;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.eptitsyn.webapp.Config;
import com.eptitsyn.webapp.model.Resume;
import java.net.MalformedURLException;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SqlStorageTest extends AbstractStorageTest{

    public SqlStorageTest() {
        super(Config.get().getSqlStorage());
    }

    @Override
    @BeforeEach
    void setUp() throws MalformedURLException {
        storage.clear();
        for (int i = 0; i < EXPECTED_SIZE; i++) {
            Resume r = new Resume(UUID.randomUUID().toString(), "John Doe");
            storage.save(r);
            expectedResumes.add(r);
        }
    }

    @Override
    @Test
    void update() {
        storage.update(expectedResumes.get(3));
        assertEquals(expectedResumes.get(3), storage.get(expectedResumes.get(3).getUuid()));
    }
}