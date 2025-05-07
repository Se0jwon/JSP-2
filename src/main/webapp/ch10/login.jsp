<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head><title>로그인</title></head>
<body>
<h2>로그인</h2>
<form action="/news.nhn?action=login" method="post">
  ID: <input type="text" name="id"><br/>
  PW: <input type="password" name="password"><br/>
  <input type="submit" value="로그인">
</form>
<c:if test="${not empty error}">
  <p style="color:red">${error}</p>
</c:if>
</body>
</html>