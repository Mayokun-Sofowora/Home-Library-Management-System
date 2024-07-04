<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Books List</title>
</head>
<body>
<h1>Books List</h1>
<table border="1">
    <tr>
        <th>Title</th>
        <th>Author</th>
        <th>Genre</th>
    </tr>
    <c:forEach var="book" items="${books}">
        <tr>
            <td>${book.title}</td>
            <td>${book.authors[0].name}</td>
            <td>${book.genres[0].name}</td>
            <td>${book.language}</td>
            <td>${book.publicationYear}</td>
            <td>${book.availableCopies}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
