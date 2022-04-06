package com.eptitsyn.webapp.storage.serializer;

import com.eptitsyn.webapp.model.ContactType;
import com.eptitsyn.webapp.model.Experience;
import com.eptitsyn.webapp.model.Organisation;
import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.model.StringListSection;
import com.eptitsyn.webapp.model.StringSection;
import com.eptitsyn.webapp.util.XmlParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements Serializer {

  private final XmlParser xmlParser;

  public XmlStreamSerializer() {
    xmlParser = new XmlParser(ContactType.class,
        Experience.class,
        Organisation.class,
        Organisation.Position.class,
        Resume.class,
        StringListSection.class,
        StringSection.class,
        URL.class);
  }

  @Override
  public void serialize(Resume r, OutputStream os) throws IOException {
    try (Writer w = new OutputStreamWriter(os, StandardCharsets.UTF_8)) {
      xmlParser.marshall(r, w);
    }
  }

  @Override
  public Resume deserialize(InputStream is) throws IOException {
    try (Reader r = new InputStreamReader(is, StandardCharsets.UTF_8)) {
      return xmlParser.unmarshall(r);
    }
  }
}
