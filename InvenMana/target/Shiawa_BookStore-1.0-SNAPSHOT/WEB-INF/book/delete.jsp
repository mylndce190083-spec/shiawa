<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Delete Book</title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"/>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <h6 class="mb-3">Delete Book</h6>
        <p>Bạn có chắc muốn xóa sách: <b>#${book.bookId} - ${book.title}</b>?</p>

        <form action="${pageContext.request.contextPath}/book" method="post">
            <input type="hidden" name="view" value="delete"/>
            <input type="hidden" name="id" value="${book.bookId}"/>
            <button class="btn btn-danger" type="submit">Delete</button>
            <a class="btn btn-secondary" href="${pageContext.request.contextPath}/book">Cancel</a>
        </form>
    </div>
</div>

</div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>
