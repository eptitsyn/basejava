package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.ExistStorageException;
import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        String uuid = r.getUuid();
        Object key = getIndex(uuid);
        if (!isExist(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        doUpdate(r, key);
    }

    protected abstract void doUpdate(Resume r, Object key);

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        Object key = getIndex(uuid);
        if (isExist(uuid)) {
            throw new ExistStorageException(uuid);
        }
        doSave(r, key);
    }

    protected abstract void doSave(Resume r, Object key);

    @Override
    public Resume get(String uuid) {
        Object key = getIndex(uuid);
        if (!isExist(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        return doGet(uuid, key);
    }

    protected abstract Resume doGet(String uuid, Object key);

    @Override
    public void delete(String uuid) {
        Object key = getIndex(uuid);
        if (!isExist(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        doDelete(uuid, key);
    }

    protected abstract void doDelete(String uuid, Object key);

    protected abstract boolean isExist(String uuid);

    protected abstract Object getIndex(String uuid);
}
