package com.eptitsyn.webapp.storage.serializer;

import com.eptitsyn.webapp.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;

public class XmlSerializer implements Serializer {
    @Override
    public void serialize(Resume r, OutputStream os) {

    }

    @Override
    public Resume deserialize(InputStream input) {
        return null;
    }
}
