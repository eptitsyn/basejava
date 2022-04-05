package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.exception.ExistStorageException;
import com.eptitsyn.webapp.exception.NotExistStorageException;
import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.storage.serializer.Serializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class PathStorage extends AbstractStorage<Path> {

    protected final Serializer serializer;
    private final Path directory;

    protected PathStorage(String dir, Serializer serializer) {
        Objects.requireNonNull(dir, "directory should not be null");
        Objects.requireNonNull(serializer);
        directory = Paths.get(dir);
        this.serializer = serializer;
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not a directory");
        }
        if (!Files.isReadable(directory) || !Files.isWritable(directory)) {
            throw new IllegalArgumentException(directory.toAbsolutePath() + " is not readable/writable");
        }
    }

    @Override
    public void clear() {
        getDirectoryList().forEach(this::doDelete);
    }

    @Override
    public int size() {
        return (int) getDirectoryList().count();
    }

    private Stream<Path> getDirectoryList() {
        try {
            return Files.list(directory);
        } catch (IOException e) {
            throw new StorageException("Directory reading error", null);
        }
    }

    private void doDelete(Path file) {
        this.doDelete(file.getFileName().toString(), file);
    }

    @Override
    protected void doSave(Resume resume, Path file) {
        if (isExist(file)) {
            throw new ExistStorageException(resume.getUuid());
        }
        try {
            Files.createFile(file);
            doUpdate(resume, file);
        } catch (IOException e) {
            throw new StorageException("IOError", file.toString(), e);
        }
    }

    @Override
    protected Resume doGet(String uuid, Path file) {
        if (!isExist(file)) {
            throw new NotExistStorageException(uuid);
        }
        try {
            return serializer.deserialize(new BufferedInputStream(Files.newInputStream(file)));
        } catch (IOException e) {
            throw new StorageException("File read error", file.getFileName().toString(), e);
        }
    }

    @Override
    protected void doDelete(String uuid, Path file) {
        if (!isExist(file)) {
            throw new NotExistStorageException(uuid);
        }
        try {
            Files.delete(file);
        } catch (IOException e) {
            throw new StorageException("Can't delete ", uuid);
        }
    }

    @Override
    protected List<Resume> doGetAll() {
        Stream<Path> files = getDirectoryList();
        List<Resume> resumes = new ArrayList<>();
        files.forEach(file -> resumes.add(doGet(file.getFileName().toString(), file)));
        return resumes;
    }

    @Override
    protected void doUpdate(Resume r, Path file) {
        if (!isExist(file)) {
            throw new NotExistStorageException(r.getUuid());
        }
        try {
            serializer.serialize(r, new BufferedOutputStream(Files.newOutputStream(file)));
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
        return Files.exists(file);
    }
}
