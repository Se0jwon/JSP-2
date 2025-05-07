<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <title>파일 업로드 및 다운로드</title>
</head>
<body>
<h2>파일 업로드</h2>
<form action="/upload" method="post" enctype="multipart/form-data">
  <input type="file" name="file">
  <button type="submit">업로드</button>
</form>

<h2>업로드된 파일 목록</h2>
<ul>
  <%
    String uploadPath = application.getRealPath("/uploads");
    java.io.File uploadDir = new java.io.File(uploadPath);
    if (!uploadDir.exists()) uploadDir.mkdir();

    java.io.File[] files = uploadDir.listFiles();
    if (files != null) {
      for (java.io.File file : files) {
  %>
  <li>
    <a href="/download?file=<%= file.getName()%>"><%= file.getName() %></a>
  </li>
  <%
      }
    }
  %>
</ul>
</body>
</html>
