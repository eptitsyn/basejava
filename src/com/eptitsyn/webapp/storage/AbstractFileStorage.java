package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.ExistStorageException;
import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    private final File directory;

    protected AbstractFileStorage(File directory) {
        Objects.requireNonNull(directory, "directory should not be null");
        if (!directory.isDirectory()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not a directory");
        }
        if (!directory.canRead() || !directory.canWrite()) {
            throw new IllegalArgumentException(directory.getAbsolutePath() + " is not readable/writable");
        }
        this.directory = directory;
    }

    @Override
    public void clear() {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.delete()) {
                    throw new StorageException("Can't delete ", file.getName());
                }
            }
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.listFiles()).length;
    }

    @Override
    protected void doSave(Resume resume, File file) {
        if (file.exists()) {
            throw new ExistStorageException(resume.getUuid());
        }
        try {
            if (!file.createNewFile()) {
                throw new IOException("Can not create file");
            }
            doWrite(resume, file);
        } catch (IOException e) {
            throw new StorageException("IOError", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume resume, File file) throws IOException;

    @Override
    protected Resume doGet(String uuid, File file) {
        if (!file.exists()) {
            throw new NotExistStorageException(uuid);
        }
        return doRead(file);
    }

    protected abstract Resume doRead(File file);

    @Override
    protected void doDelete(String uuid, File file) {
        if (!file.exists()) {
            throw new NotExistStorageException(uuid);
        }
        if (file.delete()) {
            return;
        }
        throw new StorageException("Can't delete ", uuid);
    }

    @Override
    protected List<Resume> doGetAll() {
        File[] files = directory.listFiles();
        List<Resume> resumes = new ArrayList<>();
        if (files != null) {
            for (File file : files) {
                resumes.add(doRead(file));
            }
        }
        return resumes;
    }

    @Override
    protected void doUpdate(Resume r, File file) {
        if (!file.exists()) {
            throw new NotExistStorageException(r.getUuid());
        }
        try {
            doWrite(r, file);
        } catch (IOException e) {
            throw new StorageException("Can't update. IOError", file.getName(), e);
        }
    }

    @Override
    protected File doGetSearchKey(String uuid) {
        return new File(directory, uuid);
    }

    @Override
    protected boolean isExist(File file) {
        return file.exists();
    }
}
