<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <link type="text/css"
        href="css/ui-lightness/jquery-ui-1.8.18.custom.css" rel="stylesheet" />
  <script type="text/javascript" src="js/jquery-1.7.1.min.js"></script>
  <script type="text/javascript" src="js/jquery-ui-1.8.18.custom.min.js"></script>
  <title>Add new meal</title>
</head>
<body>
<script>
  $(function() {
    $('input[name=dob]').datepicker();
  });
</script>

<form method="POST" action='mealController' name="frmAddMeal">
  Meal ID : <input type="text" readonly="readonly" name="mealId"
                   value="<c:out value="${meal.id}" />" /> <br />
  DateTime : <input
        type="text" name="dateTime"
        value="<c:out value="${meal.dateTime}" />" /> <br />
  Description : <input
        type="text" name="description"
        value="<c:out value="${meal.description}" />" /> <br />
  Calories : <input
        type="text" name="calories"
        value="<c:out value="${meal.calories}" />" /> <br />
  <%--DOB : <input
        type="text" name="dob"
        value="<fmt:formatDate pattern="yyyy-MM-dd HH:mm:ss" value="${meal.dob}" />" /> <br />--%>
  <input
        type="submit" value="Submit" />
</form>
</body>
</html>