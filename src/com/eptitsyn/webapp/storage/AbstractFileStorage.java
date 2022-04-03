package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.ExistStorageException;
import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.storage.serializer.Serializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractFileStorage extends AbstractStorage<File> {

    protected final Serializer serializer;
    private final File directory;

    protected AbstractFileStorage(File directory, Serializer serializer) {
        Objects.requireNonNull(directory, "directory should not be null");
        Objects.requireNonNull(serializer, "seralizer must not be null");
        this.serializer = serializer;
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
            doUpdate(resume, file);
        } catch (IOException e) {
            throw new StorageException("IOError", file.getName(), e);
        }
    }

    protected abstract void doWrite(Resume resume, OutputStream file) throws IOException;

    @Override
    protected Resume doGet(String uuid, File file) {
        if (!file.exists()) {
            throw new NotExistStorageException(uuid);
        }
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getName(), e);
        }
    }

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
                resumes.add(doGet(file.getName(), file));
            }
        }
        return resumes;
    }

    protected abstract Resume doRead(InputStream file) throws IOException;

    @Override
    protected void doUpdate(Resume r, File file) {
        if (!file.exists()) {
            throw new NotExistStorageException(r.getUuid());
        }
        try {
            doWrite(r, new BufferedOutputStream(new FileOutputStream(file)));
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
