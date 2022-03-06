package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListStorage extends AbstractStorage {
    List<Resume> storage = new ArrayList<>();

    @Override
    protected void doUpdate(Resume r) {
        storage.set(storage.indexOf(r), r);
    }

    @Override
    protected void doSave(Resume r) {
        storage.add(r);
    }

    @Override
    protected Resume doGet(String uuid) {
        return storage.get(storage.indexOf(new Resume(uuid)));
    }

    @Override
    protected void doDelete(String uuid) {
        storage.remove(new Resume(uuid));
    }

    @Override
    protected boolean isExist(String uuid) {
        return storage.contains(new Resume(uuid));
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
