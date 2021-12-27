package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;

import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    private Resume[] storage = new Resume[10000];
    private int count;

    public void clear() {
        Arrays.fill(storage, 0, count, null);
        count = 0;
    }

    public void save(Resume r) {
        if (count >= storage.length) {
            System.out.println("No free space for saving new resume");
            return;
        }
        if (getIndex(r.getUuid()) != -1) {
            System.out.println("Resume with uuid=" + r.getUuid() + " already exist.");
            return;
        }
        storage[count] = r;
        count++;
    }

    public Resume get(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex == -1) {
            System.out.println("Resume with uuid=" + uuid + " not found");
            return null;
        }
        return storage[resumeIndex];
    }

    public void delete(String uuid) {
        int resumeIndex = getIndex(uuid);
        if (resumeIndex == -1) {
            System.out.println("Resume with uuid=" + uuid + " not found");
            return;
        }
        count--;
        storage[resumeIndex] = storage[count];
        storage[count] = null;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    public Resume[] getAll() {
        return Arrays.copyOf(storage, count);
    }

    public int size() {
        return count;
    }

    public void update(Resume resume) {
        int resumeIndex = getIndex(resume.getUuid());
        if (resumeIndex == -1) {
            System.out.println("Resume with uuid=" + resume.getUuid() + " not found");
            return;
        }
        storage[resumeIndex] = resume;
    }

    private int getIndex(String uuid) {
        for (int i = 0; i < count; i++) {
            if (storage[i].getUuid().equals(uuid)) {
                return i;
            }
        }
        return -1;
    }
}
