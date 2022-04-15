package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage<Integer> {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count = 0;

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    public int size() {
        return count;
    }

    @Override
    public void doUpdate(Resume r, Integer key) {
        storage[key] = r;
    }

    @Override
    protected boolean isExist(Integer key) {
        return key >= 0;
    }

    @Override
    protected void doSave(Resume r, Integer key) {
        if (count >= storage.length) {
            throw new StorageException("No free space for saving new resume", r.getUuid());
        }

        putResume(r, key);
        count++;
    }

    public Resume doGet(String uuid, Integer key) {
        return storage[key];
    }

    @Override
    public void doDelete(String uuid, Integer key) {
        deallocateResume(key);
        count--;
    }

    @Override
    public List<Resume> doGetAll() {
        return Arrays.asList(Arrays.copyOf(storage, size()));
    }

    protected abstract void deallocateResume(int index);

    protected abstract void putResume(Resume resume, int index);
}
