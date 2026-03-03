<%-- 
    Document   : create
    Created on : Jan 31, 2026, 10:50:31 AM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="../include/headerAdmin.jsp" %>
<!-- Recent Sales Start -->
<div class="container-fluid pt-4 px-4">
    <div class="bg-light text-center rounded p-4">
        <div class="d-flex align-items-center justify-content-between mb-4">
            <!--<h6 class="mb-0">User List</h6>-->
            <div class="d-flex justify-content-between align-items-center">
                <h6 class="mb-0">Add Book</h6>
            </div>
            <div class="d-flex gap-4">
                <a class="btn btn-sm btn-success" href="${pageContext.request.contextPath}/book-admin?view=add">Add Book</a>
                <a href="" class="text-primary">Show All</a>
            </div>
        </div>
        <div class="row justify-content-center">
            <div class="col-lg-8">
                <div class="card shadow-sm">
                    <div class="card-body">

                        <h4 class="mb-4 text-success">
                            <i class="fa fa-plus-circle me-2"></i>Add New Book
                        </h4>

                        <form action="${pageContext.request.contextPath}/book-admin" method="post">
                            <input type="hidden" name="view" value="add"/>

                            <div class="mb-3">
                                <label class="form-label">Title</label>
                                <input type="text" name="title" class="form-control" required>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Author</label>
                                <input type="text" name="author" class="form-control" required>
                            </div>

                            <div class="row">
                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Price</label>
                                    <input type="number" step="0.01" name="price" class="form-control" required>
                                </div>

                                <div class="col-md-6 mb-3">
                                    <label class="form-label">Stock</label>
                                    <input type="number" name="stock" class="form-control" required>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label class="form-label">Category</label>
                                <select name="categoryId" class="form-select" required>
                                    <option value="">-- Select Category --</option>
                                    <c:forEach var="c" items="${categoryList}">
                                        <option value="${c.categoryId}">
                                            ${c.categoryName}
                                        </option>
                                    </c:forEach>
                                </select>
                            </div>

                            <div class="d-flex justify-content-between mt-4">
                                <button type="submit" class="btn btn-success">
                                    <i class="fa fa-save me-2"></i>Save
                                </button>

                                <a href="${pageContext.request.contextPath}/book-admin" class="btn btn-secondary">
                                    Cancel
                                </a>
                            </div>

                        </form>

                    </div>
                </div>
            </div>
        </div>

    </div>
</div>
<!-- Recent Sales End -->
<%@include file="../include/footerAdmin.jsp" %>

