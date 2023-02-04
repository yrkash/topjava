<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<table border="1">
    <caption>Meals</caption>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>
    <c:forEach var="meal" items="${meals}">


        <tr style="background-color:${meal.exceed ? 'greenyellow' : 'red'}">
            <td><c:out value="${meal.dateTimeWithoutT}" /></td>
            <td><c:out value="${meal.description}" /></td>
            <td><c:out value="${meal.calories}" /></td></tr>
    </c:forEach>

</table>
</body>

</html>