package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    @Override
    protected void deallocateResume(int index) {
        storage[index] = null;
        System.arraycopy(storage, index + 1, storage, index, count - 1 - index);
    }

    @Override
    protected void putResume(Resume resume, int index) {
        storage[count] = resume;
    }

    protected int getIndex(String uuid) {
        for (int i = 0; i < count; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
