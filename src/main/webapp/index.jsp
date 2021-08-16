<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Users</title>
    <style>
        table {
            font-family: arial, sans-serif;
            border-collapse: collapse;
            width: 100%;
        }

        td, th {
            border: 1px solid #dddddd;
            text-align: left;
            padding: 8px;
        }

        tr:nth-child(even) {
            background-color: #dddddd;
        }
    </style>
</head>
<body>
<h2>Users List</h2>
<p><a href='<c:url value="/insert" />'>Insert user</a></p>
<table style="width:100%">
    <tr>
        <th>Usr ID</th>

        <th>AGE</th>

        <th>FNAME</th>

        <th>LNAME</th>

        <th></th>
    </tr>
    <c:forEach var="user" items="${requestScope.users}">
        <tr>
            <td>${user.userId}</td>
            <td>${user.userAge}</td>
            <td>${user.firstName}</td>
            <td>${user.lastName}</td>
            <td>
                <a href='<c:url value="/edit?user_id=${user.userId}" />'>Edit</a> |
                <form method="post" action='<c:url value="/delete" />' style="display:inline;">
                    <input type="hidden" name="user_id" value="${user.userId}">
                    <input type="submit" value="Delete">
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>