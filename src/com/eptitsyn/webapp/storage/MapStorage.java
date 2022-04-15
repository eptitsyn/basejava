package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapStorage extends AbstractStorage<String> {

    final Map<String, Resume> storage = new HashMap<>();

    @Override
    protected void doUpdate(Resume r, String key) {
        storage.replace(key, r);
    }

    @Override
    protected void doSave(Resume r, String key) {
        storage.put(key, r);
    }

    @Override
    protected Resume doGet(String uuid, String key) {
        return storage.get(uuid);
    }

    @Override
    protected void doDelete(String uuid, String key) {
        storage.remove(uuid);
    }

    @Override
    protected boolean isExist(String key) {
        return storage.containsKey(key);
    }

    @Override
    protected String doGetSearchKey(String uuid) {
        return uuid;
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public List<Resume> doGetAll() {
        return new ArrayList<>(storage.values());
    }

    @Override
    public int size() {
        return storage.size();
    }
}
