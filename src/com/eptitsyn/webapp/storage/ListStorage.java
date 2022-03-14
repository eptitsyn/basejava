package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected void doUpdate(Resume r, Object key) {
        storage.set((int)key, r);
    }

    @Override
    protected void doSave(Resume r, Object key) {
        storage.add(r);
    }

    @Override
    protected Resume doGet(String uuid, Object key) {
        return storage.get((int)key);
    }

    @Override
    protected void doDelete(String uuid, Object key) {
        storage.remove(new Resume(uuid));
    }

    @Override
    protected boolean isExist(Object key) {
        return (int)key != -1;
    }

    @Override
    protected Object doGetSearchKey(String uuid) {
        return storage.indexOf(new Resume(uuid));
    }

    @Override
    public void clear() {
        storage.clear();
    }

    @Override
    public Resume[] getAll() {
        return storage.toArray(new Resume[size()]);
    }

    @Override
    public int size() {
        return storage.size();
    }


}
