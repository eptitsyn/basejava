package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.ExistStorageException;
import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        String uuid = r.getUuid();
        if (!isExist(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        doUpdate(r, getIndex(uuid));
    }

    protected abstract void doUpdate(Resume r, int index);

    @Override
    public void save(Resume r) {
        String uuid = r.getUuid();
        if (isExist(uuid)) {
            throw new ExistStorageException(uuid);
        }
        doSave(r, getIndex(uuid));
    }

    protected abstract void doSave(Resume r, int index);

    @Override
    public Resume get(String uuid) {
        if (!isExist(uuid)) {
            throw new NotExistStorageException(uuid);
        }

        return doGet(uuid, getIndex(uuid));
    }

    protected abstract Resume doGet(String uuid, int index);

    @Override
    public void delete(String uuid) {
        if (!isExist(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        doDelete(uuid, getIndex(uuid));
    }

    protected abstract void doDelete(String uuid, int index);

    protected abstract boolean isExist(String uuid);

    protected abstract int getIndex(String uuid);
}
