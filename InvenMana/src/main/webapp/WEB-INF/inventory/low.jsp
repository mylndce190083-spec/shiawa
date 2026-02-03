<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Tồn kho thấp</title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"/>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h6 class="mb-0">Danh sách tồn thấp (<= ${threshold})</h6>
            <form class="d-flex gap-2" method="get" action="${pageContext.request.contextPath}/inventory">
                <input type="hidden" name="view" value="low"/>
                <input class="form-control" type="number" min="0" name="threshold" value="${threshold}" style="width: 120px;"/>
                <button class="btn btn-success btn-sm" type="submit">Lọc</button>
            </form>
        </div>

        <div class="table-responsive">
            <table class="table text-start align-middle table-bordered table-hover mb-0">
                <thead>
                <tr class="text-success">
                    <th>ID</th>
                    <th>Title</th>
                    <th>Category</th>
                    <th>Stock</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="b" items="${bookList}">
                    <tr>
                        <td>${b.bookId}</td>
                        <td>${b.title}</td>
                        <td>${b.categoryName}</td>
                        <td><span class="badge bg-danger">${b.stock}</span></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

</div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>




