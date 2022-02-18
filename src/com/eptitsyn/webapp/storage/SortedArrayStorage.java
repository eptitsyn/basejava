package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;

import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        if (count == 0) return -1;
        Resume object = new Resume(uuid);
        int search = Arrays.binarySearch(Arrays.copyOfRange(storage, 0, count), object);
        return search >= 0 && storage[search].getUuid().equals(uuid) ? search : -1;
    }

    @Override
    protected int allocateElement(String uuid) {
        if (count == 0) return 0;
        Resume object = new Resume(uuid);
        int insertPos = Arrays.binarySearch(Arrays.copyOfRange(storage, 0, count), object);
        insertPos = (insertPos >= 0) ? insertPos : (-insertPos - 1);
        for (int i = count - 1; i > insertPos; i--) {
            storage[i + 1] = storage[i];
        }
        return insertPos;
    }

    @Override
    protected void deallocateElement(int index) {
        for (int i = index; i < count - 1; i++) {
            storage[i] = storage[i + 1];
        }
        storage[count - 1] = null;
    }
}