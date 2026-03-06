<%-- 
    Document   : post-book
    Created on : Mar 5, 2026, 3:55:12 PM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>
<!-- Recent Sales Start -->

<div class="container-fluid pt-4 px-4">
    <div class="bg-light rounded p-4">

        <h4 class="mb-4 text-success text-center">
            Post New Book
        </h4>

        <div class="row justify-content-center">
            <div class="col-lg-8">

                <form action="${pageContext.request.contextPath}/book-admin" method="post">
                    <input type="hidden" name="view" value="post"/>

                    <!-- TITLE -->
                    <div class="mb-3">
                        <label class="form-label">Title</label>
                        <input type="text" name="title" class="form-control" required>
                    </div>

                    <!-- AUTHOR -->
                    <div class="mb-3">
                        <label class="form-label">Author</label>
                        <input type="text" name="author" class="form-control" required>
                    </div>

                    <!-- DESCRIPTION -->
                    <div class="mb-3">
                        <label class="form-label">Description</label>
                        <textarea name="description" class="form-control" rows="4"></textarea>
                    </div>

                    <!-- PRICE + STOCK -->
                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label">Price</label>
                            <input type="number" name="price" class="form-control" required>
                        </div>

                        <div class="col-md-6 mb-3">
                            <label class="form-label">Stock</label>
                            <input type="number" name="stock" class="form-control" required>
                        </div>
                    </div>

                    <!-- CATEGORY -->
                    <div class="mb-3">
                        <label class="form-label">Category</label>
                        <select name="categoryId" class="form-select">
                            <c:forEach var="c" items="${categoryList}">
                                <option value="${c.categoryId}">
                                    ${c.categoryName}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <!-- BUTTON -->
                    <div class="text-center mt-4">
                        <button class="btn btn-success px-4">
                            Post Book
                        </button>

                        <a href="${pageContext.request.contextPath}/book-admin"
                           class="btn btn-secondary px-4">
                            Cancel
                        </a>
                    </div>

                </form>

            </div>
        </div>

    </div>
</div>

<!-- Recent Sales End -->
<%@include file="../include/footerAdmin.jsp" %>