<%@ page import="com.eptitsyn.webapp.model.ContactType" %>
<%@ page import="com.eptitsyn.webapp.model.Resume" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html; charset=UTF-8" %>
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
    <table>
        <caption>All resume</caption>
        <colgroup>
            <col/>
            <col/>
        </colgroup>
        <tbody>
        <tr>
            <th>Name</th>
            <th>Email</th>
        </tr>
        <c:forEach items="${resumes}" var="resume">
            <jsp:useBean id="resume" type="com.eptitsyn.webapp.model.Resume"/>
            <tr>
                <td><a href="resume?action=get&uuid=${resume.uuid}">${resume.fullName}</a></td>
                <td>${resume.getContact(ContactType.EMAIL)}</td>
                <td><a href="resume?action=edit&uuid=${resume.uuid}">Edit</a></td>
                <td><a href="resume?action=delete&uuid=${resume.uuid}">Delete</a></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
<nav>
    <a href="resume?action=add">Add</a>
    <a href="resume?action=clear">Clear</a>
</nav>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
