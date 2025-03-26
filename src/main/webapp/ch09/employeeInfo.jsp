<%--
  Created by IntelliJ IDEA.
  User: wingwogus
  Date: 2025. 3. 26.
  Time: 오전 1:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Employee Info</title>
</head>
<body>
    <h2>직원정보</h2>[<a href="/employeeControl">새로고침</a>]
    <hr>

    <table border="1">
        <tr>
        <th>id</th>
        <th>이름</th>
        </tr>
        <c:forEach items="${employees}" var="s">
            <tr>
                <td>${s.id}</td>
                <td>${s.name}</td>
            </tr>
        </c:forEach>
    </table>

    <h2>직원 추가</h2>
    <hr>
    <form method="post" action="/employeeControl?action=insert">
        <label>이름</label>
        <input type="text" name="name"><br>
        <button type="submit">등록</button>
    </form>
</body>
</html>
