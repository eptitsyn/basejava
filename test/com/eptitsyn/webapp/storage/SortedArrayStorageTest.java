package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SortedArrayStorageTest extends AbstractArrayStorageTest {


    public SortedArrayStorageTest() {
        super(new SortedArrayStorage());
    }

    @Test
    void getAll() {
        Resume[] actual = storage.getAll();
        assertEquals(actual.length, EXPECTED_SIZE);

        Resume[] expected = Arrays.copyOf(expectedResumes, EXPECTED_SIZE);

        Arrays.sort(expected);
        assertArrayEquals(actual, expected);
    }
}