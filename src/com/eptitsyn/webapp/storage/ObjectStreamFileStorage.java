package com.eptitsyn.webapp.storage;

import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.storage.serializer.ObjectStreamSerializer;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

public class ObjectStreamFileStorage extends AbstractFileStorage {
    protected ObjectStreamFileStorage(String directory) {
        super(new File(directory), new ObjectStreamSerializer());
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
