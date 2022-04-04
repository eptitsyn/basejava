package com.eptitsyn.webapp.util;

import com.eptitsyn.webapp.model.AbstractSection;
import com.eptitsyn.webapp.model.ContactType;
import com.eptitsyn.webapp.model.SectionType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import sun.swing.SwingUtilities2;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.EnumMap;

public class JsonParser {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(AbstractSection.class, new JsonSectionAdapter<>())
            .enableComplexMapKeySerialization()
            .setPrettyPrinting()
            .create();

    public static <T> T read(Reader reader, Class<T> clazz) {
        return GSON.fromJson(reader, clazz);
    }

    public static <T> void write(T object, Writer writer) {
        GSON.toJson(object, writer);
    }

}
