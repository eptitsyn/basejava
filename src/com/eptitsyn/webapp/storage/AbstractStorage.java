package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.ExistStorageException;
import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.model.Resume;

import java.util.List;

public abstract class AbstractStorage implements Storage {

    @Override
    public void update(Resume r) {
        Object key = getSearchKey(r.getUuid(), true);
        doUpdate(r, key);
    }

    @Override
    public void save(Resume r) {
        Object key = getSearchKey(r.getUuid(), false);
        doSave(r, key);
    }

    protected abstract void doSave(Resume r, Object key);

    @Override
    public Resume get(String uuid) {
        Object key = getSearchKey(uuid, true);
        return doGet(uuid, key);
    }

    protected abstract Resume doGet(String uuid, Object key);

    @Override
    public void delete(String uuid) {
        Object key = getSearchKey(uuid, true);
        doDelete(uuid, key);
    }

    protected abstract void doDelete(String uuid, Object key);

    @Override
    public List<Resume> getAllSorted() {
        List<Resume> result = doGetAll();
        result.sort(Resume.RESUME_NAME_UUID_COMPARATOR);
        return result;
    }

    protected abstract List<Resume> doGetAll();

    protected abstract void doUpdate(Resume r, Object key);

    protected abstract Object doGetSearchKey(String uuid);

    protected abstract boolean isExist(Object key);

    private Object getSearchKey(String uuid, boolean exist) {
        Object key = doGetSearchKey(uuid);
        if (isExist(key) != exist) {
            if (exist) {
                throw new NotExistStorageException(uuid);
            }
            throw new ExistStorageException(uuid);
        }
        return key;
    }
}