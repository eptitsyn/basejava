package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.storage.serializer.ObjectStreamSerializer;
import com.eptitsyn.webapp.storage.serializer.Serializer;

import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamPathStorage extends AbstractPathStorage {

    public ObjectStreamPathStorage(String dir, Serializer serializer) {
        super(dir, serializer);
    }

    public ObjectStreamPathStorage(String dir) {
        this(dir, new ObjectStreamSerializer());
    }

    @Override
    protected void doWrite(Resume resume, OutputStream os) {
        serializer.serialize(resume, os);
    }

    @Override
    protected Resume doRead(InputStream is) {
        return serializer.deserialize(is);
    }
}
