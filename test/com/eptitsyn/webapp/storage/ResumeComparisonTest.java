package com.eptitsyn.webapp.storage;

import static com.eptitsyn.webapp.ResumeTestData.generateTestResume;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import com.eptitsyn.webapp.model.Organisation;
import com.eptitsyn.webapp.model.Organisation.Position;
import com.eptitsyn.webapp.model.Resume;
import com.eptitsyn.webapp.model.SectionType;
import com.eptitsyn.webapp.model.StringSection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ResumeComparisonTest {

  @BeforeEach
  void setUp() {

  }

  @Test
  void TestNotEquals() throws MalformedURLException {
    Resume r1 = generateTestResume("1", "John Doe");
    Resume r2 = generateTestResume("1", "John Doe");
    assertEquals(r1, r2);
    r1.putSection(SectionType.PERSONAL, new StringSection("test 123"));
    assertNotEquals(r1, r2);
  }

  @Test
  void PositionEquals() {
    Position p1 = new Position( 2000, Month.JANUARY, "some_title", "description some");
    Position p2 = new Position( 2000, Month.JANUARY, "some_title", "description some");
    assertEquals(p1, p2);
  }

  @Test
  void PositionNotEquals() {
    Position p1 = new Position( 2000, Month.JANUARY, "some_title", "description some");
    Position p2 = new Position( 2001, Month.JANUARY, "some_title", "description some");
    Position p3 = new Position( 2000, Month.FEBRUARY, "some_title", "description some");
    Position p4 = new Position( 2000, Month.JANUARY, "some_title_wrong", "description some");
    Position p5 = new Position( 2000, Month.JANUARY, "some_title", "description some wrong");
    assertNotEquals(p2, p1);
    assertNotEquals(p3, p1);
    assertNotEquals(p4, p1);
    assertNotEquals(p5, p1);
  }

  @Test
  void OrganisationEquals() throws MalformedURLException {
    Position[] p1 = {new Position( 2000, Month.JANUARY, "some_title", "description some"),
        new Position( 2000, Month.JANUARY, "some_title", "description some")};
    Organisation o1 = new Organisation("ACME org.", new URL("http://acme.org"), Arrays.asList(p1));
    Organisation o2 = new Organisation("ACME org.", new URL("http://acme.org"), Arrays.asList(p1));
    assertEquals(o1, o2);
  }

  @Test
  void OrganisationNotEquals() throws MalformedURLException{
    Position[] p1 = {new Position( 2000, Month.JANUARY, "some_title", "description some"),
        new Position( 2001, Month.JANUARY, "some_title", "description some")};
    Position[] p2 = {new Position( 2000, Month.JANUARY, "some_title", "description some"),
        new Position( 2002, Month.JANUARY, "some_title", "description some")};
    Organisation o1 = new Organisation("ACME org.", new URL("http://acme.org"), Arrays.asList(p1));
    Organisation o2 = new Organisation("ACME org.", new URL("http://acme.org"), Arrays.asList(p2));
    assertNotEquals(o1, o2);
  }
}
