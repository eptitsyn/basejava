package com.eptitsyn.webapp.storage;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage extends AbstractArrayStorage {

    protected int getIndex(String uuid) {
        for (int i = 0; i < count; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected int allocateResume(String uuid) {
        return count;
    }

    @Override
    protected void deallocateResume(int index) {
        storage[index] = null;
        System.arraycopy(storage, index + 1, storage, index, count - 1 - index);
    }
}
