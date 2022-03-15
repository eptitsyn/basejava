package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapObjectStorage extends AbstractStorage {
    final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected Object doGetSearchKey(String uuid) {
        return storage.get(uuid);
    }

    @Override
    protected void doUpdate(Resume r, Object key) {
        storage.replace(r.getUuid(), r);
    }

    @Override
    protected void doSave(Resume r, Object key) {
        storage.put(r.getUuid(), r);
    }

    @Override
    protected Resume doGet(String uuid, Object key) {
        return (Resume) key;
    }

    @Override
    protected void doDelete(String uuid, Object key) {
        storage.remove(uuid);
    }

    @Override
    protected boolean isExist(Object key) {
        return key != null;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.values().toArray(new Resume[0]);
    }

    @Override
    public int size() {
        return storage.size();
    }
}
