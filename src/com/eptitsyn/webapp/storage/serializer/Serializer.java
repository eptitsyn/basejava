package com.eptitsyn.webapp.storage.serializer;

import com.eptitsyn.webapp.model.Resume;

import java.io.InputStream;
import java.io.OutputStream;


public interface Serializer {
    void serialize(Resume r, OutputStream os);
    Resume deserialize(InputStream input);
}
