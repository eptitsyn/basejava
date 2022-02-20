package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;
import java.util.Arrays;

public class SortedArrayStorage extends AbstractArrayStorage {

    @Override
    protected int getIndex(String uuid) {
        if (count == 0) return -1;
        Resume object = new Resume(uuid);
        return Arrays.binarySearch(Arrays.copyOfRange(storage, 0, count), object);
    }

    @Override
    protected int allocateElement(String uuid) {
        if (count == 0) return 0;
        int insertPos = getIndex(uuid);
        insertPos = (insertPos >= 0) ? insertPos : (-(insertPos) - 1);
        if (count - insertPos >= 0) System.arraycopy(storage, insertPos, storage, insertPos + 1, count - insertPos);
        return insertPos;
    }

    @Override
    protected void deallocateElement(int index) {
        if (count - index > 0) {
            System.arraycopy(storage, index + 1, storage, index, count - 1 - index);
        }
        storage[count - 1] = null;
    }
}