package com.eptitsyn.webapp.storage.serializer;


import com.eptitsyn.webapp.model.ContactType;
import com.eptitsyn.webapp.model.Experience;
import com.eptitsyn.webapp.model.Organisation;
import com.eptitsyn.webapp.model.Organisation.Position;
import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.model.SectionType;
import com.eptitsyn.webapp.model.StringListSection;
import com.eptitsyn.webapp.model.StringSection;
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
        switch (entry.getKey()) {
          case PERSONAL:
          case OBJECTIVE:
            dos.writeUTF(((StringSection) entry.getValue()).getText());
            break;
          case EXPERIENCE:
          case EDUCATION:
            serializeCollection(((Experience) entry.getValue()).getOrganisations(), dos, item -> {
              dos.writeUTF(item.getName());
              writeObjectOrNull(dos, item.getWebsite().toString());
              serializeCollection(item.getPositions(), dos, position -> {
                writeDate(dos, position.getStartDate());
                writeDate(dos, position.getEndDate());
                dos.writeUTF(position.getTitle());
                writeObjectOrNull(dos, position.getDescription());
              });
            });
            break;
          case ACHIEVEMENTS:
          case QUALIFICATIONS:
            serializeCollection(((StringListSection) entry.getValue()).getList(), dos,
                dos::writeUTF);
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
      deserializeCollection(dis,
          () -> resume.addContact(ContactType.valueOf(dis.readUTF()), dis.readUTF()));
      deserializeCollection(dis, () -> {
        SectionType name = SectionType.valueOf(dis.readUTF());
        switch (name) {
          case PERSONAL:
          case OBJECTIVE:
            resume.putSection(name, new StringSection(dis.readUTF()));
            break;
          case ACHIEVEMENTS:
          case QUALIFICATIONS:
            resume.putSection(name, new StringListSection(deserializeList(dis, dis::readUTF)));
            break;
          case EXPERIENCE:
          case EDUCATION:
            List<Organisation> lo = deserializeList(dis, () -> {
              String orgName = dis.readUTF();
              URL orgURL = readObjectOrNull(dis, () -> new URL(dis.readUTF()));
              List<Position> positions = deserializeList(dis, () -> {
                LocalDate start = readDate(dis);
                LocalDate end = readDate(dis);
                String title = dis.readUTF();
                String description = readObjectOrNull(dis, dis::readUTF);
                return new Position(start, end, title, description);
              });
              return new Organisation(orgName, orgURL, positions);
            });
            resume.putSection(name, new Experience(lo));
        }
      });
      return resume;
    }
  }

  private void writeDate(DataOutputStream dos, LocalDate position) throws IOException {
    dos.writeInt(position.getYear());
    dos.writeInt(position.getMonthValue());
  }

  private LocalDate readDate(DataInputStream dis) throws IOException {
    return LocalDate.of(dis.readInt(), dis.readInt(), 1);
  }

  private <T> void writeObjectOrNull(DataOutputStream dos, T t) throws IOException {
    dos.writeBoolean(t != null);
    if (t != null) {
      dos.writeUTF(t.toString());
    }
  }

  private <T> T readObjectOrNull(DataInputStream dis, FuncWithException<T> reader)
      throws IOException {
    if (dis.readBoolean()) {
      return reader.result();
    }
    return null;
  }

  private <T> void serializeCollection(Collection<T> collection, DataOutputStream dos,
      ConsumerWithException<T> consumer) throws IOException {
    dos.writeInt(collection.size());
    for (T t : collection) {
      consumer.accept(t);
    }
  }

  private <T> List<T> deserializeList(DataInputStream dis, FuncWithException<T> func)
      throws IOException {
    List<T> list = new ArrayList<>();
    deserializeCollection(dis, () -> list.add(func.result()));
    return list;
  }

  private void deserializeCollection(DataInputStream dis, ProcessorWithException processor)
      throws IOException {
    int size = dis.readInt();
    for (int i = 0; i < size; i++) {
      processor.process();
    }
  }

  @FunctionalInterface
  private interface ConsumerWithException<T> {

    void accept(T t) throws IOException;
  }

  @FunctionalInterface
  private interface ProcessorWithException {

    void process() throws IOException;
  }

  @FunctionalInterface
  private interface FuncWithException<T> {

    T result() throws IOException;
  }
}
