<jsp:useBean id="resume" scope="request" type="com.eptitsyn.webapp.model.Resume"/>
<%@ page import="com.eptitsyn.webapp.model.ContactType" %>
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
<nav>
    <form action="resume">
        <input type="hidden" name="uuid" value="${resume.uuid}">
        <input type="hidden" name="action" value="edit">
        <input type="submit" value="Edit"/>
    </form>
</nav>
<section>
    <h1>${resume.fullName}</h1>
    <%--    uuid ${resume.uuid}--%>
</section>
<c:if test="${resume.contacts.size() > 0}">
    <section>
        <h2>Contacts</h2>
        <c:forEach items="${resume.contacts}" var="contact">
            <jsp:useBean id="contact" type="java.util.Map.Entry"/>
            ${contact.key.toString()} ${contact.value} <br/>
        </c:forEach>
    </section>
</c:if>
<c:forEach items="${resume.sections}" var="section">
    <section>
        <jsp:useBean id="section" type="java.util.Map.Entry<com.eptitsyn.webapp.model.SectionType,
                com.eptitsyn.webapp.model.AbstractSection>"/>
        <h2>${section.key.toString()}</h2>
        <c:choose>
            <c:when test="${section.value['class'].name == 'com.eptitsyn.webapp.model.StringSection'}">
                ${section.value}
            </c:when>
            <c:when test="${section.value['class'].name == 'com.eptitsyn.webapp.model.StringListSection'}">
                <ul>
                    <c:forEach items="${section.value.getList()}" var="listItem">
                        <li>${listItem}</li>
                    </c:forEach>
                </ul>
            </c:when>
            <c:when test="${section.value['class'].name == 'com.eptitsyn.webapp.model.Experience'}">
                <ul>
                    <c:forEach items="${section.value.getOrganisations()}" var="organisation">
                        <jsp:useBean id="organisation" type="com.eptitsyn.webapp.model.Organisation"/>
                        <li><h3>
                            <c:if test="${organisation.website != null}"><a
                                href="${organisation.website.toString()}"></c:if>
                                ${organisation.name}
                            <c:if test="${organisation.website != null}"></a></c:if>
                        </h3>
                            <ul>
                                <c:forEach items="${organisation.positions}" var="position">
                                    <li>${position.title} ${position.startDate} - ${position.endDate} <br/>
                                        <p>${position.description}</p>
                                    </li>
                                </c:forEach>
                            </ul>
                            <p></p>
                        </li>
                    </c:forEach>
                </ul>
            </c:when>
        </c:choose>
            <%--        <p>${section.value}</p>--%>
    </section>
</c:forEach>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
