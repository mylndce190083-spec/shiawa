<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <h6 class="mb-4">Delete Book</h6>

        <form action="${pageContext.request.contextPath}/book" method="post">
            <input type="hidden" name="view" value="delete"/>
            <input type="hidden" name="id" value="${book.bookId}"/>

            <div class="row g-4 align-items-start">
                <div class="col-md-4 col-lg-3 text-center">
                    <c:choose>
                        <c:when test="${not empty book.urlImg}">
                            <img src="${pageContext.request.contextPath}/${book.urlImg}"
                                 alt="${book.title}"
                                 class="img-fluid border rounded"
                                 style="max-height: 300px; object-fit: cover;"/>
                        </c:when>
                        <c:otherwise>
                            <div class="border rounded d-flex align-items-center justify-content-center text-muted"
                                 style="height: 300px;">
                                No image
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>

                <div class="col-md-8 col-lg-9">
                    <div class="table-responsive">
                        <table class="table table-bordered mb-0">
                            <tbody>
                            <tr>
                                <th style="width: 35%">Book ID</th>
                                <td>${book.bookId}</td>
                            </tr>
                            <tr>
                                <th>Title</th>
                                <td>${book.title}</td>
                            </tr>
                            <tr>
                                <th>Author</th>
                                <td>${book.author}</td>
                            </tr>
                            <tr>
                                <th>Category</th>
                                <td>${book.categoryName}</td>
                            </tr>
                            <tr>
                                <th>Price</th>
                                <td>${book.price}</td>
                            </tr>
                            <tr>
                                <th>Publisher</th>
                                <td>${book.publisher}</td>
                            </tr>
                            <tr>
                                <th>Discount</th>
                                <td>${book.discount}%</td>
                            </tr>
                            <tr>
                                <th>Stock</th>
                                <td>${book.stock}</td>
                            </tr>

                            <tr>
                                <th>Created At</th>
                                <td>${book.createdAt}</td>
                            </tr>
                            <tr>
                                <th>Description</th>
                                <td class="text-muted">(Không có dữ liệu mô tả)</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                    <div class="mt-4 d-flex gap-2 justify-content-end">
                        <button class="btn btn-danger" type="submit">
                            <i class="fa fa-trash me-1"></i>Confirm Delete
                        </button>
                        <a class="btn btn-secondary" href="${pageContext.request.contextPath}/book">Cancel</a>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>

</div>
</div>

<script src="${pageContext.request.contextPath}/assets/js/main.js"></script>
</body>
</html>
