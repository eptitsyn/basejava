package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;
import java.util.Arrays;

public abstract class AbstractArrayStorage implements Storage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count = 0;

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    @Override
    public void update(Resume r) {
        int index = getIndex(r.getUuid());
        if (index == -1) {
            System.out.println("Resume " + r.getUuid() + " not exist");
            return;
        }
        storage[index] = r;
    }

    @Override
    public void save(Resume r) {
        if (count >= storage.length) {
            System.out.println("No free space for saving new resume");
            return;
        }
        if (getIndex(r.getUuid()) >= 0) {
            System.out.println("Resume with uuid=" + r.getUuid() + " already exist.");
            return;
        }
        int index = allocateElement(r.getUuid());
        storage[index] = r;
        count++;
    }

    public Resume get(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " not exist");
            return null;
        }
        return storage[index];
    }

    @Override
    public void delete(String uuid) {
        int index = getIndex(uuid);
        if (index == -1) {
            System.out.println("Resume " + uuid + " not exist");
            return;
        }
        deallocateElement(index);
        count--;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    public int size() {
        return count;
    }

    protected abstract void deallocateElement(int index);

    protected abstract int getIndex(String uuid);

    protected abstract int allocateElement(String uuid);
}
