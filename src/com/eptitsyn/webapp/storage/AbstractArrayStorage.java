package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;

import java.util.Arrays;

public abstract class AbstractArrayStorage extends AbstractStorage {

    protected static final int STORAGE_LIMIT = 10000;
    protected Resume[] storage = new Resume[STORAGE_LIMIT];
    protected int count = 0;

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    @Override
    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    public int size() {
        return count;
    }

    @Override
    public void doUpdate(Resume r) {
        int index = getIndex(r.getUuid());
        storage[index] = r;
    }

    @Override
    protected void doSave(Resume r) {
        if (count >= storage.length) {
            throw new StorageException("No free space for saving new resume", r.getUuid());
        }

        putResume(r, getIndex(r.getUuid()));
        count++;
    }

    public Resume doGet(String uuid) {
        int index = getIndex(uuid);
        return storage[index];
    }

    @Override
    public void doDelete(String uuid) {
        int index = getIndex(uuid);
        if (index < 0) {
            throw new NotExistStorageException(uuid);
        }
        deallocateResume(index);
        count--;
    }

    @Override
    protected boolean isExist(String uuid) {
        return getIndex(uuid) >= 0;
    }

    protected abstract void deallocateResume(int index);

    protected abstract void putResume(Resume resume, int index);

}
