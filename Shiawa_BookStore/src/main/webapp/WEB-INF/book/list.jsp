<%-- 
    Document   : list
    Created on : Jan 31, 2026, 10:33:09 AM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@include file="../include/headerAdmin.jsp" %>
<!-- Recent Sales Start -->
<div class="container-fluid pt-4 px-4">
    <div class="bg-light text-center rounded p-4">
        <c:if test="${not empty sessionScope.msg}">
            <div class="alert alert-${sessionScope.msgType} alert-dismissible fade show" role="alert">
                ${sessionScope.msg}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>

            <!-- Xóa message sau khi hiển thị -->
            <c:remove var="msg" scope="session"/>
            <c:remove var="msgType" scope="session"/>
        </c:if>

        <div class="d-flex align-items-center justify-content-between mb-4">
            <!--<h6 class="mb-0">User List</h6>-->
            <div class="d-flex justify-content-between align-items-center">
                <h6 class="mb-0">Book List</h6>
            </div>
            <div class="d-flex gap-3 align-items-center">
                <form action="${pageContext.request.contextPath}/book-admin"
                      method="get">

                    <select name="categoryId"
                            class="form-select form-select-sm"
                            onchange="this.form.submit()">

                        <option value="">All Categories</option>

                        <c:forEach var="c" items="${categoryList}">
                            <option value="${c.categoryId}"
                                    ${c.categoryId == selectedCategoryId ? "selected" : ""}>
                                ${c.categoryName}
                            </option>
                        </c:forEach>

                    </select>
                </form>

                <form action="${pageContext.request.contextPath}/book-admin" method="get" class="d-flex">
                    <input type="text" 
                           name="keyword"
                           value="${keyword}"
                           class="form-control form-control-sm me-2"
                           placeholder="Search book by name">
                    <button type="submit" class="btn btn-sm btn-outline-success">
                        <i class="fa fa-search"></i>
                    </button>
                </form>

                <c:if test="${not empty keyword}">
                    <a href="${pageContext.request.contextPath}/book-admin"
                       class="btn btn-sm btn-secondary">
                        Show All
                    </a>
                </c:if>

                <div class="d-flex gap-4">
                    <a class="btn btn-sm btn-warning"
                       href="${pageContext.request.contextPath}/book-request?action=list"
                       class="nav-item nav-link ${'book-request'.equals(pagePrimary) ? 'active' : ''}">
                        Accept Book Request
                    </a>

                    <a class="btn btn-sm btn-success"
                       href="${pageContext.request.contextPath}/book-admin?view=post">
                        Post New Book
                    </a>
                </div>
            </div>

        </div>
        <c:if test="${not empty searchMsg}">
            <div class="alert alert-warning text-start mb-3">
                ${searchMsg}
            </div>
        </c:if>

        <div class="table-responsive">
            <c:choose>
                <c:when test="${empty bookList}">
                    <!-- chỉ hiện thông báo, không hiện bảng -->
                </c:when>
                <c:otherwise>
                    <table class="table text-start align-middle table-bordered table-hover mb-0">
                        <thead>
                            <tr class="text-success">
                                <th>ID</th>
                                <th>Title</th>
                                <th>Author</th>
                                <th>Category</th>
                                <th>Price</th>
                                <th>Stock</th>
                                <th class="text-center">Action</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="b" items="${bookList}">
                                <tr>
                                    <td>${b.bookId}</td>
                                    <td>${b.title}</td>
                                    <td>${b.author}</td>
                                    <td>${b.categoryName}</td>
                                    <td>
                                        <fmt:formatNumber value="${b.price}" type="number" groupingUsed="true" maxFractionDigits="0"/>
                                    </td>
                                    <td>${b.stock}</td>
                                    <td class="text-center">
                                        <a href="${pageContext.request.contextPath}/book-admin?view=detail&id=${b.bookId}"
                                           class="btn btn-sm btn-primary">
                                            Detail
                                        </a>
                                        <a href="${pageContext.request.contextPath}/book-admin?view=edit&id=${b.bookId}"
                                           class="btn btn-sm btn-warning">
                                            Edit
                                        </a>

                                        <a href="${pageContext.request.contextPath}/book-admin?view=delete&id=${b.bookId}"
                                           class="btn btn-sm btn-danger">
                                            Delete
                                        </a>

                                    </td>
                                </tr>
                            </c:forEach>

                        </tbody>

                    </table>
                    <div class="d-flex justify-content-center mt-4">

                        <!-- Previous -->
                        <c:if test="${currentPageNum > 1}">
                            <a class="btn btn-sm btn-outline-secondary me-2"
                               href="book-admin?page=${currentPageNum-1}&keyword=${keyword}&categoryId=${selectedCategoryId}">
                                <<
                            </a>
                        </c:if>

                        <!-- Page number -->
                        <c:forEach begin="1" end="${totalPage}" var="i">
                            <a class="btn btn-sm ${i == currentPageNum ? 'btn-primary' : 'btn-outline-primary'} me-1"
                               href="book-admin?page=${i}&keyword=${keyword}&categoryId=${selectedCategoryId}">
                                ${i}
                            </a>
                        </c:forEach>

                        <!-- Next -->
                        <c:if test="${currentPageNum < totalPage}">
                            <a class="btn btn-sm btn-outline-secondary ms-2"
                               href="book-admin?page=${currentPageNum+1}&keyword=${keyword}&categoryId=${selectedCategoryId}">
                                >>
                            </a>
                        </c:if>

                    </div>
                </c:otherwise>
            </c:choose>
        </div>

    </div>
</div>
<!-- Recent Sales End -->
<%@include file="../include/footerAdmin.jsp" %>