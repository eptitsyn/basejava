package com.eptitsyn.webapp.storage.serializer;

import com.eptitsyn.webapp.model.*;
import com.eptitsyn.webapp.util.XmlParser;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class XmlStreamSerializer implements Serializer {
    private XmlParser xmlParser;

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
