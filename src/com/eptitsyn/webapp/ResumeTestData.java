package com.eptitsyn.webapp;

import com.eptitsyn.webapp.model.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ResumeTestData {
    public static void main(String[] args) throws MalformedURLException {
        Resume r = new Resume("James Bond");

        r.putSection(SectionType.PERSONAL,
                new StringSection("Аналитический склад ума, сильная логика, креативность, инициативность. Пурист кода и архитектуры."));
        r.putSection(SectionType.OBJECTIVE,
                new StringSection("Ведущий стажировок и корпоративного обучения по Java Web и Enterprise технологиям"));

        r.putSection(SectionType.ACHIEVEMENTS,
                new StringListSection(Arrays.asList("С 2013 года: разработка проектов \"Разработка Web приложения\",\"Java Enterprise\", \"Многомодульный maven. Многопоточность. XML (JAXB/StAX). Веб сервисы (JAX-RS/SOAP). Удаленное взаимодействие (JMS/AKKA)\". Организация онлайн стажировок и ведение проектов. Более 1000 выпускников.",
                        "Реализация двухфакторной аутентификации для онлайн платформы управления проектами Wrike. Интеграция с Twilio, DuoSecurity, Google Authenticator, Jira, Zendesk."
                )));
        r.putSection(SectionType.QUALIFICATIONS,
                new StringListSection(Arrays.asList("JEE AS: GlassFish (v2.1, v3), OC4J, JBoss, Tomcat, Jetty, WebLogic, WSO2",
                "Version control: Subversion, Git, Mercury, ClearCase, Perforce",
                "DB: PostgreSQL(наследование, pgplsql, PL/Python), Redis (Jedis), H2, Oracle,",
                "MySQL, SQLite, MS SQL, HSQLDB"
        )));

        List<Organisation> qualificationRecords = Arrays.asList(
                new Organisation(LocalDate.now().minusYears(5),
                        LocalDate.now().minusYears(4),
                        "Junior developer",
                        "Some job description",
                        new URL("https://website")),
                new Organisation(LocalDate.now().minusYears(3),
                        LocalDate.now().minusYears(1),
                        "Middle developer",
                        "Some another job description",
                        new URL("https://website"))
        );

        r.putSection(SectionType.EXPERIENCE, new Experience(qualificationRecords));

        Experience educationSection = new Experience(new ArrayList<>());
        educationSection.addOrganisation(new Organisation(
                LocalDate.parse("2013-03-01"),
                LocalDate.parse("2013-05-01"),
                "Coursera",
                "Functional Programming Principles in Scala\" by Martin Odersky",
                new URL("https://coursera.org"))
        );
        educationSection.addOrganisation(new Organisation(
                LocalDate.parse("2011-03-01"),
                LocalDate.parse("2011-04-01"),
                "Luxsoft",
                "Курс \"Объектно-ориентированный анализ ИС. Концептуальное моделирование на UML.\"",
                new URL("https://luxoft.com"))
        );
        r.putSection(SectionType.EDUCATION, educationSection);

        r.putContacts(ContactType.PHONE, "+7 800 555 35 35");
        r.putContacts(ContactType.EMAIL, "james.bond@mi6.gov.uk");
        r.putContacts(ContactType.SKYPE, "james.bond");

        System.out.println(r);
    }
}
