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
    <!-- 부트스트랩 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-SgOJa3DmI69IUzQ2PVdRZhwQ+dy64/BUtbMJw1MZ8t5HZApcHrRKUc4W0kG879m7" crossorigin="anonymous">

    <!-- 부트스트랩 JavaScript -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.5/dist/js/bootstrap.bundle.min.js" integrity="sha384-k6d4wzSIapyDyv1kpU366/PK5hCdSbCRGRCMv+eplOQJWyd1fbcAu9OCUj5zNLiq" crossorigin="anonymous"></script>
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