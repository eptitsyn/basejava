package com.eptitsyn.webapp;

import com.eptitsyn.webapp.model.*;
import com.eptitsyn.webapp.util.DateUtil;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class ResumeTestData {

  public static Resume generateTestResume(String uuid, String fullName) throws MalformedURLException {
    Resume resume = new Resume(uuid, fullName);

    resume.putSection(SectionType.PERSONAL,
            new StringSection(
                    "Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
    resume.putSection(SectionType.OBJECTIVE,
            new StringSection(
                    "Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

    resume.putSection(SectionType.ACHIEVEMENTS,
            new StringListSection(Arrays.asList(
                    "С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                    "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk."
            )));
    resume.putSection(SectionType.QUALIFICATIONS,
            new StringListSection(Arrays.asList(
                    "JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                    "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                    "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                    "MySQL, SQLite, MS SQL, HSQLDB"
            )));

    List<Organisation> qualificationRecords = Arrays.asList(
            new Organisation(
                    "Luxsoft", new URL("https://luxsoft.com"), Arrays.asList(new Organisation.Position(
                            DateUtil.of(2005, Month.of(1)),
                            DateUtil.of(2006, Month.of(5)),
                            "Junior developer",
                            "Some job description"),
                    new Organisation.Position(
                            DateUtil.of(2006, Month.of(5)),
                            DateUtil.of(2007, Month.of(9)),
                            "Middle developer",
                            "Some another job description")
            )
            ),
            new Organisation(
                    "Yota", new URL("https://yota.ru"), Arrays.asList(new Organisation.Position(
                            DateUtil.of(2007, Month.of(9)),
                            DateUtil.of(2009, Month.of(1)),
                            "Middle developer",
                            "Some job description"),
                    new Organisation.Position(
                            DateUtil.of(2009, Month.of(9)),
                            DateUtil.of(2015, Month.of(1)),
                            "Senior developer",
                            "Some another job description")
            )
            )
    );
    resume.putSection(SectionType.EXPERIENCE, new Experience(qualificationRecords));

    Experience educationSection = new Experience(Arrays.asList(
            new Organisation("Coursera", new URL("https://coursera.org"), Collections.singletonList(
                    new Organisation.Position(
                            LocalDate.parse("2013-03-01"),
                            LocalDate.parse("2013-05-01"),
                            "\"Functional Programming Principles in Scala\" by Martin Odersky", " "))
            ),
            new Organisation("Luxsoft", new URL("https://luxsoft.com"), Collections.singletonList(
                    new Organisation.Position(
                            LocalDate.parse("2011-03-01"),
                            LocalDate.parse("2011-04-01"),
                            "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                            " "))
            ),
            new Organisation("Acme Ltd.", null, Collections.singletonList(
                    new Organisation.Position(
                            LocalDate.parse("2011-03-01"),
                            LocalDate.parse("2011-04-01"),
                            "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                            " "))
            )
    ));

    resume.putSection(SectionType.EDUCATION, educationSection);

    resume.putContacts(ContactType.PHONE, "+7 800 555 35 35");
    resume.putContacts(ContactType.EMAIL, "james.bond@mi6.gov.uk");
    resume.putContacts(ContactType.SKYPE, "james.bond");

    return resume;
  }

  public static void main(String[] args) throws MalformedURLException {

    Resume r = generateTestResume(UUID.randomUUID().toString(), "James Bond");
    System.out.println(r);
  }
}

