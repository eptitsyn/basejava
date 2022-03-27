package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected void deallocateResume(int index) {
        if (count - index > 0) {
            System.arraycopy(storage, index + 1, storage, index, count - 1 - index);
        }
        storage[count - 1] = null;
    }

    @Override
    protected void putResume(Resume resume, int index) {
        if (count == 0) {
            storage[0] = resume;
            return;
        }
        int insertPos = -index - 1;
        System.arraycopy(storage, insertPos, storage, insertPos + 1, count - insertPos);
        storage[insertPos] = resume;
    }

    @Override
    protected Integer doGetSearchKey(String uuid) {
        if (count == 0) return -1;
        Resume object = new Resume(uuid, "");
        return Arrays.binarySearch(storage, 0, count, object);
    }
}