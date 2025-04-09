<%--
  Created by IntelliJ IDEA.
  User: wingwogus
  Date: 2025. 4. 8.
  Time: 오후 11:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <!-- 부트스트랩 css -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
          rel="stylesheet" integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Na00vYz1ztcQTwFspdj3yD65VohhpuuCOmLASjC"
          crossorigin="anonymous">

    <!-- 자바스크립트 라이브러리 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-MrcW6ZMFY1zCkAL8N1+NtUVF0sA7MsXsP1UyJoM9P4YL0uNs5tF+JpXcXn/tWtIaxVXM"
            crossorigin="anonymous"></script>
</head>
<body>
    <div class="container w-75 mt-5 mx-auto">
        <h2>뉴스 목록</h2>
        <hr>
        <ul class="list-group">
            <c:forEach var="news" items="${newsList}" varStatus="status">
                <li class="list-group-item list-group-item-action d-flex justify-content-between align-items-center">
                    <a href="news.nhn?action=getNews&aid=${news.aid}" class="text-decoration-none">
                        [${status.count}] ${news.title}, ${news.date}
                    </a> <!-- 뉴스 제목 -->
                    <a href="news.nhn?action=deleteNews&aid=${news.aid}">
                        <span class="badge bg-secondary">&times;</span>
                    </a>
                </li>
            </c:forEach>
        </ul>
        <hr>
        <c:if test="${error != null}">
            <div class="alert alert-danger alert-dismissible fade show mt-3">
                에러 발생: ${error}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <button class="btn btn-outline-info mb-3" type="button" data-bs-toggle="collapse"
                data-bs-target="#addForm" aria-expanded="false" aria-controls="addForm">
            뉴스 등록
        </button>
        <a href="/news.nhn?action=delAllNews">
            <button class="btn btn-outline-info mb-3" type="button" data-bs-toggle="collapse"
                    data-bs-target="#addForm" aria-expanded="false" aria-controls="addForm">
                뉴스 전체 삭제
            </button>
        </a>


        <div class="collapse" id="addForm">
            <div class="card card-body">
                <form method="post" action="news.nhn?action=addNews" enctype="multipart/form-data">
                    <label class="form-label">제목</label>
                    <input type="text" name="title" class="form-control">

                    <label class="form-label">이미지</label>
                    <input type="file" name="file" class="form-control">

                    <label class="form-label">기사내용</label>
                    <textarea cols="50" rows="5" name="content" class="form-control"></textarea>

                    <button type="submit" class="btn btn-success mt-3">저장</button>
                </form>
            </div>
        </div>
    </div>
</body>
</html>