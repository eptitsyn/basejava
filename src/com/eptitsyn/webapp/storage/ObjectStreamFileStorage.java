package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.storage.serializer.ObjectStreamSerializer;
import com.eptitsyn.webapp.storage.serializer.Serializer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamFileStorage extends AbstractFileStorage {

    public ObjectStreamFileStorage(File directory, Serializer serializer) {
        super(directory, serializer);
    }

    public ObjectStreamFileStorage(String directory) {
        this(new File(directory), new ObjectStreamSerializer());
    }

    @Override
    protected void doWrite(Resume resume, OutputStream os) throws IOException {
        serializer.serialize(resume, os);
    }

    @Override
    protected Resume doRead(InputStream is) throws IOException {
        return serializer.deserialize(is);
    }
}
