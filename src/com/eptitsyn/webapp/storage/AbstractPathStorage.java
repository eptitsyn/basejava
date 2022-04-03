package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.ExistStorageException;
import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.storage.serializer.Serializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public abstract class AbstractPathStorage extends AbstractStorage<Path> {

    protected final Serializer serializer;
    private final Path directory;

    protected AbstractPathStorage(String dir, Serializer serializer) {
        Objects.requireNonNull(dir, "directory should not be null");
        Objects.requireNonNull(serializer);
        directory = Paths.get(dir);
        this.serializer = serializer;
        if (!directory.toFile().isDirectory()) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not a directory");
        }
        if (!directory.toFile().canRead() || !directory.toFile().canWrite()) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not readable/writable");
        }
    }

    @Override
    public void clear() {
        try {
            Files.list(directory).forEach(this::doDelete);
        } catch (IOException e) {
            throw new StorageException("Path delete error", null);
        }
    }

    @Override
    public int size() {
        return Objects.requireNonNull(directory.toFile().listFiles()).length;
    }

    private void doDelete(Path file) {
        this.doDelete(file.getFileName().toString(), file);
    }

    @Override
    protected void doSave(Resume resume, Path file) {
        if (file.toFile().exists()) {
            throw new ExistStorageException(resume.getUuid());
        }
        try {
            if (!file.toFile().createNewFile()) {
                throw new IOException("Can not create file");
            }
            doUpdate(resume, file);
        } catch (IOException e) {
            throw new StorageException("IOError", file.toString(), e);
        }
    }

    protected abstract void doWrite(Resume resume, OutputStream file) throws IOException;

    @Override
    protected Resume doGet(String uuid, Path file) {
        if (!file.toFile().exists()) {
            throw new NotExistStorageException(uuid);
        }
        try {
            return doRead(new BufferedInputStream(new FileInputStream(file.toString())));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(String uuid, Path file) {
        if (!file.toFile().exists()) {
            throw new NotExistStorageException(uuid);
        }
        if (file.toFile().delete()) {
            return;
        }
        throw new StorageException("Can't delete ", uuid);
    }

    @Override
    protected List<Resume> doGetAll() {
        String[] files = directory.toFile().list();
        List<Resume> resumes = new ArrayList<>();
        if (files != null) {
            for (String file : files) {
                resumes.add(doGet(file, directory.resolve(file)));
            }
        }
        return resumes;
    }

    protected abstract Resume doRead(InputStream file);

    @Override
    protected void doUpdate(Resume r, Path file) {
        if (!file.toFile().exists()) {
            throw new NotExistStorageException(r.getUuid());
        }
        try {
            doWrite(r, new BufferedOutputStream(new FileOutputStream(file.toString())));
        } catch (IOException e) {
            throw new StorageException("Can't update. IOError", file.getFileName().toString(), e);
        }
    }

    @Override
    protected Path doGetSearchKey(String uuid) {
        return directory.resolve(uuid);
    }

    @Override
    protected boolean isExist(Path file) {
        return file.toFile().exists();
    }
}
