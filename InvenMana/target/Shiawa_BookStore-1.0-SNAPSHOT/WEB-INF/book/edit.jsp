<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Edit Book</title>
    <link href="${pageContext.request.contextPath}/assets/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/assets/css/style.css" rel="stylesheet">
</head>
<body>
<jsp:include page="/WEB-INF/include/header.jsp"/>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">
        <h6 class="mb-4">Edit Book</h6>
        <form action="${pageContext.request.contextPath}/book" method="post">
            <input type="hidden" name="view" value="edit"/>
            <input type="hidden" name="id" value="${book.bookId}"/>

            <div class="row g-3">
                <div class="col-12">
                    <label class="form-label">Title</label>
                    <input class="form-control" name="title" value="${book.title}" required/>
                </div>
                <div class="col-12">
                    <label class="form-label">Author</label>
                    <input class="form-control" name="author" value="${book.author}" required/>
                </div>
                <div class="col-12">
                    <label class="form-label">Price</label>
                    <input class="form-control" type="number" step="0.01" name="price" value="${book.price}" required/>
                </div>
                <div class="col-12">
                    <label class="form-label">Stock</label>
                    <input class="form-control" type="number" name="stock" value="${book.stock}" required/>
                </div>
                <div class="col-12">
                    <label class="form-label">Category</label>
                    <select class="form-select" name="categoryId" required>
                        <c:forEach var="c" items="${categorys}">
                            <option value="${c.categoryId}" ${c.categoryId == book.categoryId ? 'selected' : ''}>
                                ${c.categoryName}
                            </option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="mt-4 d-flex gap-2">
                <button class="btn btn-success" type="submit">Save</button>
                <a class="btn btn-secondary" href="${pageContext.request.contextPath}/book">Cancel</a>
            </div>
        </form>
    </div>
</div>

</div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>
