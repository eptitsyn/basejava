package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.storage.serializer.ObjectStreamSerializer;

import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamPathStorage extends AbstractPathStorage {
    protected ObjectStreamPathStorage(String dir) {
        super(dir, new ObjectStreamSerializer());
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
