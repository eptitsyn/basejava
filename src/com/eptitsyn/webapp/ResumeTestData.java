package com.eptitsyn.webapp;

import com.eptitsyn.webapp.model.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.*;

public class ResumeTestData {

    public static Resume generateTestResume(String uuid, String fullName) throws MalformedURLException {
        Resume resume = new Resume(uuid, fullName);
        //TODO
        resume.putSection(SectionType.PERSONAL,
                new StringSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        resume.putSection(SectionType.OBJECTIVE,
                new StringSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        resume.putSection(SectionType.ACHIEVEMENTS,
                new StringListSection(Arrays.asList("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                        "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk."
                )));
        resume.putSection(SectionType.QUALIFICATIONS,
                new StringListSection(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                        "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                        "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                        "MySQL, SQLite, MS SQL, HSQLDB"
                )));

                List<Organisation> qualificationRecords = Arrays.asList(
                new Organisation(
                        Arrays.asList(new Position(
                                LocalDate.now().minusYears(5),
                                LocalDate.now().minusYears(4),
                                "Junior developer",
                                "Some job description"),
                                new Position(
                                        LocalDate.now().minusYears(4),
                                        LocalDate.now().minusYears(3),
                                        "Middle developer",
                                        "Some another job description")
                        ),
                        "Luxsoft",
                        new URL("https://luxsoft.com")
                ),
                        new Organisation(
                                Arrays.asList(new Position(
                                                LocalDate.now().minusYears(3),
                                                LocalDate.now().minusYears(2),
                                                "Middle developer",
                                                "Some job description"),
                                        new Position(
                                                LocalDate.now().minusYears(2),
                                                LocalDate.now().minusYears(1),
                                                "Senior developer",
                                                "Some another job description")
                                ),
                                "Yota",
                                new URL("https://yota.ru")
                        )
        );
        resume.putSection(SectionType.EXPERIENCE, new Experience(qualificationRecords));

        Experience educationSection = new Experience(Arrays.asList(
                new Organisation(Collections.singletonList(
                        new Position(
                                LocalDate.parse("2013-03-01"),
                                LocalDate.parse("2013-05-01"),
                                "\"Functional Programming Principles in Scala\" by Martin Odersky", ""))
                        , "Coursera",
                        new URL("https://coursera.org")),
                new Organisation(Collections.singletonList(
                        new Position(
                                LocalDate.parse("2011-03-01"),
                                LocalDate.parse("2011-04-01"),
                                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"", ""))
                        , "Luxsoft",
                        new URL("https://luxsoft.com"))
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
