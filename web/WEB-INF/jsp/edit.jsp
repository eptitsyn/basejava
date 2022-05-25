<jsp:useBean id="resume" scope="request" type="com.eptitsyn.webapp.model.Resume"/>
<%@ page import="com.eptitsyn.webapp.model.ContactType" %>
<%@ page import="com.eptitsyn.webapp.model.SectionType" %>
<%@ page import="com.eptitsyn.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
    <link rel="stylesheet" href="css/style.css">
    <title>List all resumes</title>
</head>
<body>
<jsp:include page="fragments/header.jsp"/>
<section>
    <form action="resume" method="post">
        <input type="hidden" name="action" value="edit">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <label>FullName
            <input type="text" name="fullName" value="${resume.fullName}" pattern="^\w{1,}(\s\w{1,})*$"/>
        </label>
        <h2>Contacts</h2>
        <c:forEach items="${ContactType.values()}" var="contact">
            <jsp:useBean id="contact" type="com.eptitsyn.webapp.model.ContactType"/>
            <label>
                    ${contact.toString()}
                <input type="text" name="${contact.name()}" value="${resume.contacts.get(contact)}"/><br>
            </label>
        </c:forEach>
        <c:forEach items="${SectionType.values()}" var="section">
            <jsp:useBean id="section" type="com.eptitsyn.webapp.model.SectionType"/>
            <label><h2>${section.toString()}</h2>
                <c:choose>
                    <c:when test="${section.name() == 'PERSONAL' || section.name() == 'OBJECTIVE'}">
                        <input type="text" name="${section.name()}" value="${resume.sections.get(section).getText()}">
                    </c:when>
                    <c:when test="${section.name() == 'ACHIEVEMENTS' || section.name() == 'QUALIFICATIONS'}">
                        <textarea name="${section.name()}">${resume.getSection(section).getString()}</textarea>
                    </c:when>
                    <c:when test="${section.name() == 'EXPERIENCE' || section.name() == 'EDUCATION'}">
                        <input type="hidden" name="${section.name()}_orgCount"
                               value="${resume.getSection(section).organisations.size()}">
                        <c:forEach items="${resume.getSection(section).organisations}" var="organisation"
                                   varStatus="orgConter">
                            <jsp:useBean id="organisation" type="com.eptitsyn.webapp.model.Organisation"/>
                            <hr>
                            <label>Name<input type="text" name="${section.name()}_${orgConter.index}_name"
                                              value="${organisation.name}"></label><br>
                            <label>URL<input type="url" name="${section.name()}_${orgConter.index}_url"
                                             value="${organisation.website.toString()}"
                                             pattern="https?:\/\/(www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b([-a-zA-Z0-9()@:%_\+.~#?&//=]*)">
                            </label><br>
                            <input type="hidden" name="${section.name()}_${orgConter.index}_posCount"
                                   value="${organisation.positions.size()}">
                            <c:forEach items="${organisation.positions}" var="position" varStatus="posCounter">
                                <jsp:useBean id="position" type="com.eptitsyn.webapp.model.Organisation.Position"/>
                                <label>Start date<input type="date"
                                                        name="${section.name()}_${orgConter.index}_${posCounter.index}_startDate"
                                                        value="${position.startDate.toString()}"></label><br>
                                <label>End date<input type="date"
                                                      name="${section.name()}_${orgConter.index}_${posCounter.index}_endDate"
                                                      value="${position.endDate.toString()}"></label><br>
                                <label>Title<input type="text"
                                                   name="${section.name()}_${orgConter.index}_${posCounter.index}_title"
                                                   value="${position.title}"></label><br>
                                <label>Description<input type="text"
                                                         name="${section.name()}_${orgConter.index}_${posCounter.index}_description"
                                                         value="${position.description}"></label><br>
                            </c:forEach>
                        </c:forEach>
                    </c:when>
                </c:choose>
            </label>
        </c:forEach>
        <br>
        <button type="submit">Save</button>
        <button type="button" onclick="window.location='resume'">Back</button>
    </form>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
