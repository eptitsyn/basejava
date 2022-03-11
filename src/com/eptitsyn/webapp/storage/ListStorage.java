package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;

import java.util.ArrayList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    private final List<Resume> storage = new ArrayList<>();

    @Override
    protected void doUpdate(Resume r, int index) {
        storage.set(index, r);
    }

    @Override
    protected void doSave(Resume r, int index) {
        storage.add(r);
    }

    @Override
    protected Resume doGet(String uuid, int index) {
        return storage.get(index);
    }

    @Override
    protected void doDelete(String uuid, int index) {
        storage.remove(new Resume(uuid));
    }

    @Override
    protected boolean isExist(String uuid) {
        return storage.contains(new Resume(uuid));
    }

    @Override
    protected int getIndex(String uuid) {
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
