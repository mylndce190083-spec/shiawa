<%-- 
    Document   : delete
    Created on : Jan 31, 2026, 10:50:46 AM
    Author     : BA LIEM
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../include/headerAdmin.jsp" %>

<div class="container-fluid pt-4 px-4">
    <div class="bg-light text-center rounded p-4">
        <div class="d-flex align-items-center justify-content-between mb-4">
            
            <div class="d-flex justify-content-between align-items-center">
                <h6 class="mb-0">Delete Book</h6>
            </div>                         
        </div>


        <div class="row">

            <div class="col-md-4 text-center">
                <c:set var="primaryFound" value="false"/>

                <c:forEach var="img" items="${bookImages}">
                    <c:if test="${img.primary}">
                        <img src="${pageContext.request.contextPath}/${img.imageUrl}"
                             class="img-fluid rounded border"
                             style="max-height: 300px;">
                        <c:set var="primaryFound" value="true"/>
                    </c:if>
                </c:forEach>

                <c:if test="${!primaryFound}">
                    <img src="${pageContext.request.contextPath}/assets/img/no-image.png"
                         class="img-fluid rounded border"
                         style="max-height: 300px;">
                </c:if>                               
            </div>

            
            <div class="col-md-8">
                <form action="${pageContext.request.contextPath}/book-admin" method="post">
                    <input type="hidden" name="view" value="delete">
                    <input type="hidden" name="bookId" value="${book.bookId}">

                    <table class="table table-bordered text-start">
                        <tr>
                            <th width="30%">Book ID</th>
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
                            <td>
                            <fmt:formatNumber value="${book.price}" groupingUsed="true" maxFractionDigits="0"/> 
                            </td>
                        </tr>
                        <tr>
                            <th>Stock</th>
                            <td>${book.stock}</td>
                        </tr>
                        <tr>
                            <th>Status</th>
                            <td>
                                <c:choose>
                                    <c:when test="${book.isActive}">
                                        <span class="badge bg-success">Active</span>
                                    </c:when>
                                    <c:otherwise>
                                        <span class="badge bg-danger">Inactive</span>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th>Created At</th>
                            <td>${book.createdAt}</td>
                        </tr>
                        <tr>
                            <th>Description</th>
                            <td>${book.description}</td>
                        </tr>

                    </table>

                    
                    <div class="mt-3">
                        <button type="submit" class="btn btn-danger"
                                onclick="return confirm('Are you sure you want to delete this book?')">
                            <i class="fa fa-trash me-2"></i>Confirm Delete
                        </button>

                        <a href="${pageContext.request.contextPath}/book-admin"
                           class="btn btn-secondary ms-2">
                            Cancel
                        </a>
                    </div>
                </form>

            </div>
        </div>


    </div>
</div>
<%@include file="../include/footerAdmin.jsp" %>