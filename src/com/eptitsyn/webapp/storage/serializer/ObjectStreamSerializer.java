package com.eptitsyn.webapp.storage.serializer;


import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.Resume;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectStreamSerializer implements Serializer {

    @Override
    public void serialize(Resume resume, OutputStream os) {
        try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
            oos.writeObject(resume);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resume deserialize(InputStream is) {
        try (ObjectInputStream ois = new ObjectInputStream(is)) {
            return (Resume) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new StorageException("Error read resume", null, e);
        }
    }
}
