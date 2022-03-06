package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.ExistStorageException;
import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.model.Resume;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        if (!isExist(r.getUuid())) {
            throw new NotExistStorageException(r.getUuid());
        }
        doUpdate(r);
    }

    protected abstract void doUpdate(Resume r);

    @Override
    public void save(Resume r) {
        if (isExist(r.getUuid())) {
            throw new ExistStorageException(r.getUuid());
        }
        doSave(r);
    }

    protected abstract void doSave(Resume r);

    @Override
    public Resume get(String uuid) {
        if (!isExist(uuid)) {
            throw new NotExistStorageException(uuid);
        }

        return doGet(uuid);
    }

    protected abstract Resume doGet(String uuid);

    @Override
    public void delete(String uuid) {
        if (!isExist(uuid)) {
            throw new NotExistStorageException(uuid);
        }
        doDelete(uuid);
    }

    protected abstract void doDelete(String uuid);

    protected abstract boolean isExist(String uuid);
}
