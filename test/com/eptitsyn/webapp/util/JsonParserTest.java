package com.eptitsyn.webapp.util;

import com.eptitsyn.webapp.model.AbstractSection;
import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.model.StringSection;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static com.eptitsyn.webapp.ResumeTestData.generateTestResume;
import static org.junit.jupiter.api.Assertions.assertEquals;

class JsonParserTest {

  @Test
  void testResume() throws Exception {
    Resume resume = generateTestResume(UUID.randomUUID().toString(), "John Smith");
    String json = JsonParser.write(resume);
    System.out.println(json);
    Resume resumeRead = JsonParser.read(json, Resume.class);
    assertEquals(resume, resumeRead);
  }

  @Test
  void write() {
    AbstractSection section1 = new StringSection("Objective1");
    String json = JsonParser.write(section1, AbstractSection.class);
    System.out.println(json);
    AbstractSection section2 = JsonParser.read(json, AbstractSection.class);
    assertEquals(section1, section2);
  }
}