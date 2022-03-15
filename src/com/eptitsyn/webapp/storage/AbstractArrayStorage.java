package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractArrayStorage extends AbstractStorage {

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
    public void doUpdate(Resume r, Object key) {
        storage[(int) key] = r;
    }

    @Override
    protected boolean isExist(Object key) {
        return (int) key >= 0;
    }

    @Override
    protected void doSave(Resume r, Object key) {
        if (count >= storage.length) {
            throw new StorageException("No free space for saving new resume" , r.getUuid());
        }

        putResume(r, (int) key);
        count++;
    }

    public Resume doGet(String uuid, Object key) {
        return storage[(int) key];
    }

    @Override
    public void doDelete(String uuid, Object key) {
        deallocateResume((int) key);
        count--;
    }

    @Override
    public List<Resume> doGetAll() {
        return new ArrayList<>(Arrays.asList(storage));
    }

    protected abstract void deallocateResume(int index);

    protected abstract void putResume(Resume resume, int index);

}
