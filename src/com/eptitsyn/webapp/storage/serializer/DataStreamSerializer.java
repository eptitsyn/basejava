package com.eptitsyn.webapp.storage.serializer;


import com.eptitsyn.webapp.exception.StorageException;
import com.eptitsyn.webapp.model.ContactType;
import com.eptitsyn.webapp.model.Resume;
import com.sun.xml.internal.txw2.output.StreamSerializer;

import java.io.*;
import java.util.Map;

public class DataStreamSerializer implements Serializer {

    @Override
    public void serialize(Resume resume, OutputStream os) {
        try (DataOutputStream dos = new DataOutputStream(os)) {
            dos.writeUTF(resume.getUuid());
            dos.writeUTF(resume.getFullName());
            Map<ContactType, String> contacts = resume.getContacts();
            dos.writeInt(contacts.size());
            for (Map.Entry<ContactType, String> entry  :  contacts.entrySet()) {
                dos.writeUTF(entry.getKey().name());
                dos.writeUTF(entry.getValue());
            }
            //TODO imp sections
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Resume deserialize(InputStream is) throws IOException {
        try (DataInputStream dis = new DataInputStream(is)) {
            String uuid = dis.readUTF();
            String fullName = dis.readUTF();
            Resume resume = new Resume(uuid, fullName);
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF());
            }
            return null;
        }
    }
}
