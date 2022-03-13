package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;

import java.util.HashMap;
import java.util.Map;

public class MapStorage extends AbstractStorage {
    final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doUpdate(Resume r, Object key) {
        storage.replace((String) key, r);
    }

    @Override
    protected void doSave(Resume r, Object key) {
        storage.put((String) key, r);
    }

    @Override
    protected Resume doGet(String uuid, Object key) {
        return storage.get(uuid);
    }

    @Override
    protected void doDelete(String uuid, Object key) {
        storage.remove(uuid);
    }

    @Override
    protected boolean isExist(String uuid) {
        return storage.containsKey(uuid);
    }

    @Override
    protected Object getIndex(String uuid) {
        return uuid;
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
