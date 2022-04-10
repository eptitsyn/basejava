package com.eptitsyn.webapp.storage.serializer;


import com.eptitsyn.webapp.model.ContactType;
import com.eptitsyn.webapp.model.Experience;
import com.eptitsyn.webapp.model.Organisation;
import com.eptitsyn.webapp.model.Organisation.Position;
import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.model.SectionType;
import com.eptitsyn.webapp.model.StringListSection;
import com.eptitsyn.webapp.model.StringSection;
import com.eptitsyn.webapp.util.LocalDateAdapter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class DataStreamSerializer implements Serializer {

  @Override
  public void serialize(Resume resume, OutputStream os) throws IOException {
    try (DataOutputStream dos = new DataOutputStream(os)) {
      dos.writeUTF(resume.getUuid());
      dos.writeUTF(resume.getFullName());

      serializeCollection(resume.getContacts().entrySet(), dos, entry -> {
        dos.writeUTF(entry.getKey().name());
        dos.writeUTF(entry.getValue());
      });

      serializeCollection(resume.getSections().entrySet(), dos, entry -> {
        dos.writeUTF(entry.getKey().name());
        dos.writeUTF(entry.getValue().getClass().getSimpleName());
        switch (entry.getValue().getClass().getSimpleName()) {
          case "StringSection":
              dos.writeUTF(((StringSection)entry.getValue()).getText());
            break;
          case "Experience":
            serializeCollection(((Experience) entry.getValue()).getOrganisations(), dos, item -> {
              dos.writeUTF(item.getName());
              dos.writeUTF(item.getWebsite().toString());
              serializeCollection(item.getPositions(), dos, position -> {
                dos.writeUTF(position.getStartDate().toString());
                dos.writeUTF(position.getEndDate().toString());
                dos.writeUTF(position.getTitle());
                dos.writeUTF(position.getDescription());
              });
            });
            break;
          case "StringListSection":
            serializeCollection(((StringListSection) entry.getValue()).getList(), dos, dos::writeUTF);
            break;
        }
      });
    }
  }

  @Override
  public Resume deserialize(InputStream is) throws IOException {
    try (DataInputStream dis = new DataInputStream(is)) {
      String uuid = dis.readUTF();
      String fullName = dis.readUTF();
      Resume resume = new Resume(uuid, fullName);
      deserializeCollection(dis, () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
      deserializeCollection(dis, ()-> {
        SectionType name = SectionType.valueOf(dis.readUTF());
        switch (dis.readUTF()) {
          case "StringSection":
            resume.putSection(name, new StringSection(dis.readUTF()));
            break;
          case "StringListSection":
            resume.putSection(name, new StringListSection(deserializeList(dis, dis::readUTF)));
            break;
          case "Experience":
            List<Organisation> lo = deserializeList(dis, () -> {
              String orgName = dis.readUTF();
              URL orgURL = new URL(dis.readUTF());
              List<Position> positions = deserializeList(dis, () -> {
                //todo parse date
                String start = dis.readUTF();
                String end = dis.readUTF();
                String title = dis.readUTF();
                String description = dis.readUTF();
                return new Position(LocalDate.parse(start), LocalDate.parse(end), title, description);
              });
              return new Organisation(orgName, orgURL, positions);
            });
            resume.putSection(name, new Experience(lo));
        }
      });
      return resume;
    }
  }

  private <T> void serializeCollection(Collection<T> collection, DataOutputStream dos,
      ConsumerWithException<T> consumer)
      throws IOException {
    dos.writeInt(collection.size());
    for (T t : collection) {
      consumer.accept(t);
    }
  }

  private <T> List<T> deserializeList(DataInputStream dis, FuncWithException<T> func) throws IOException{
    List<T> list = new ArrayList<>();
    deserializeCollection(dis, () -> { list.add(func.result());
    });
    return list;
  }

  private <T> void deserializeCollection(DataInputStream dis, ProcessorWithException processor) throws IOException {
    int size = dis.readInt();
    for (int i = 0; i < size; i++) {
      processor.process();
    }
  }

  private interface ConsumerWithException<T> {
    void accept(T t) throws IOException;
  }

  private interface ProcessorWithException {
    void process() throws IOException;
  }

  private interface FuncWithException<T> {
    T result() throws IOException;
  }
}
